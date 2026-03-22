package com.contacts.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.contacts.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contacts.Dto.ContactDTO;
import com.contacts.Entity.Contact;
import com.contacts.Entity.Users;
import com.contacts.Repository.ContactRepo;
import com.contacts.mapper.ContactMapper;

@Service
public class ContactService {
	
    @Autowired
    private ContactRepo contactRepository;

    public ContactDTO save(ContactDTO dto, Users user) {
        Contact contact = ContactMapper.toEntity(dto);
        contact.setUser(user);
        return ContactMapper.toDTO(contactRepository.save(contact));
    }

    public List<ContactDTO> getAll(Users user) {
        return contactRepository.findByUser(user)
                .stream()
                .map(ContactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO getById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Contact not found"));
        return ContactMapper.toDTO(contact);
    }

    public ContactDTO update(Long id, ContactDTO dto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Contact not found"));

        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        contact.setAddress(dto.getAddress());
        contact.setCompany(dto.getCompany());
        contact.setNotes(dto.getNotes());

        return ContactMapper.toDTO(contactRepository.save(contact));
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    public List<ContactDTO> search(String q) {
        return contactRepository
                .findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContaining(q, q, q)
                .stream()
                .map(ContactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContactDTO> getFavourites(Users user) {
        return contactRepository.findByUserAndIsFavouriteTrue(user)
                .stream()
                .map(ContactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO toggleFavourite(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Contact Not Found"));

        Boolean fav = contact.getIsFavourite();
        contact.setIsFavourite(fav == null ? true : !fav);

        return ContactMapper.toDTO(contactRepository.save(contact));
    }
}
