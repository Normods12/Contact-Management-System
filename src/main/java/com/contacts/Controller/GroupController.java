package com.contacts.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.contacts.Dto.ContactResponse;
import com.contacts.Dto.CreateGroupRequest;
import com.contacts.Dto.GroupResponse;
import com.contacts.Dto.MoveContactRequest;
import com.contacts.Dto.UpdatedGroupRequest;
import com.contacts.Service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    //Create Group
    @PostMapping
    public GroupResponse createGroup(@RequestBody CreateGroupRequest request) {
        return groupService.createGroup(request);
    }

    //Get All Groups
    @GetMapping
    public List<GroupResponse> getAllGroups() {
        return groupService.getAllGroups();
    }

    //Update Group
    @PutMapping("/{id}")
    public GroupResponse updateGroup(
            @PathVariable Long id,
            @RequestBody UpdatedGroupRequest request) {
        return groupService.updateGroup(id, request);
    }

    //Delete Group
    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Long id) {
        return groupService.deleteGroup(id);
    }

    //Get Contacts in Group
    @GetMapping("/{id}/contacts")
    public List<ContactResponse> getContactsByGroup(@PathVariable Long id) {
        return groupService.getContactsByGroup(id);
    }

    //Move Contact to Group
    @PatchMapping("/contacts/{id}/move-group")
    public ContactResponse moveContact(
            @PathVariable Long id,
            @RequestBody MoveContactRequest request) {
        return groupService.moveContactToGroup(id, request);
    }
}