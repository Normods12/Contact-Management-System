package com.contacts.Service;

import com.contacts.Exception.ResourceNotFoundException;
import com.contacts.Exception.UnauthorizedResourceAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.contacts.Dto.ContactResponse;
import com.contacts.Dto.CreateGroupRequest;
import com.contacts.Dto.GroupResponse;
import com.contacts.Dto.MoveContactRequest;
import com.contacts.Dto.UpdatedGroupRequest;
import com.contacts.Entity.Contact;
import com.contacts.Entity.Group;
import com.contacts.Entity.Users;
import com.contacts.Repository.ContactRepository;
import com.contacts.Repository.GroupRepository;
import com.contacts.Repository.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final ContactRepository contactRepository;
    private final UserRepo userRepository;

    //Get logged-in user
    private Users getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Users user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }

    //Create Group
    public GroupResponse createGroup(CreateGroupRequest request) {

        Users user = getCurrentUser();

        Group group = new Group();
        group.setName(request.getName());
        group.setColorTag(request.getColorTag());
        group.setUser(user);

        Group saved = groupRepository.save(group);

        return mapToGroupResponse(saved);
    }

    //Get All Groups
    public List<GroupResponse> getAllGroups() {

        Users user = getCurrentUser();

        List<Group> groups = groupRepository.findByUserId(Long.valueOf(user.getId()));

        return groups.stream()
                .map(this::mapToGroupResponse)
                .toList();
    }

    //Update Group
    public GroupResponse updateGroup(Long id, UpdatedGroupRequest request) {

        Users user = getCurrentUser();

        Group group = groupRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Group not found"));



        if (group.getUser() == null || 
        	    group.getUser().getId() != user.getId()) {

        	    throw new UnauthorizedResourceAccessException("Unauthorized Access");
        	}
        group.setName(request.getName());
        group.setColorTag(request.getColorTag());

        Group updated = groupRepository.save(group);

        return mapToGroupResponse(updated);
    }

    // Delete Group
    public String deleteGroup(Long id) {

        Users user = getCurrentUser();

        Group group = groupRepository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Group not found"));


        if (group.getUser() == null || 
        	    group.getUser().getId() != user.getId()) {

        	    throw new UnauthorizedResourceAccessException("Unauthorized Access");
        	}

        // 🧩 Remove group reference from contacts
        if (group.getContacts() != null) {
            group.getContacts().forEach(contact -> contact.setGroup(null));
        }

        groupRepository.delete(group);

        return "Group deleted successfully";
    }

    // Get Contacts in Group
    public List<ContactResponse> getContactsByGroup(Long groupId) {

        Users user = getCurrentUser();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()->  new ResourceNotFoundException("Group not found"));

        if (group.getUser() == null || 
        	    group.getUser().getId() != user.getId()) {

        	    throw new UnauthorizedResourceAccessException("Unauthorized Access");
        	}
        List<Contact> contacts = contactRepository.findByGroupId(groupId);

        return contacts.stream()
                .map(this::mapToContactResponse)
                .toList();
    }

    // ✅Move Contact to Group
    public ContactResponse moveContactToGroup(Long contactId, MoveContactRequest request) {

        Users user = getCurrentUser();

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(()->  new ResourceNotFoundException("Contact not found"));




        if (contact.getUser() == null || 
        	    contact.getUser().getId() != user.getId()) {

        	    throw new UnauthorizedResourceAccessException("Unauthorized Access");
         }

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(()->  new ResourceNotFoundException("Group not found"));


        if (group.getUser() == null || 
        	    group.getUser().getId() != user.getId()) {

        	    throw new UnauthorizedResourceAccessException("Unauthorized Access");
        	}

        // Move contact
        contact.setGroup(group);

        Contact updated = contactRepository.save(contact);

        return mapToContactResponse(updated);
    }

    //MAPPERS

    private GroupResponse mapToGroupResponse(Group group) {
        GroupResponse res = new GroupResponse();
        res.setId(group.getId());
        res.setName(group.getName());
        res.setColorTag(group.getColorTag());
        res.setCreatedDate(group.getCreatedDate());
        return res;
    }

    private ContactResponse mapToContactResponse(Contact c) {
        ContactResponse res = new ContactResponse();
        res.setId(c.getId());
        res.setFirstName(c.getFirstName());
        res.setLastName(c.getLastName());
        res.setPhone(c.getPhone());
        res.setEmail(c.getEmail());
        res.setIsFavourite(c.getIsFavourite());
        return res;
    }
}