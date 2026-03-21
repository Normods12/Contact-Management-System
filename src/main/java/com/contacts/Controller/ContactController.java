package com.contacts.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contacts.Dto.ContactDTO;
import com.contacts.Entity.Users;
import com.contacts.Service.ContactService;
import com.contacts.Service.UserService;

@RestController
@RequestMapping("/api")
public class ContactController {
	
	 @Autowired
	    private ContactService contactService;

	    @Autowired
	    private UserService userService;

	    // CREATE
	    @PostMapping("/contacts")
	    public ContactDTO create(@RequestBody ContactDTO dto, @RequestParam Long userId) {
	        Users user = userService.getById(userId);
	        return contactService.save(dto, user);
	    }

	    // GET ALL
	    @GetMapping("/contacts")
	    public List<ContactDTO> getAll(@RequestParam Long userId) {
	        Users user = userService.getById(userId);
	        return contactService.getAll(user);
	    }

	    // GET BY ID
	    @GetMapping("/{id}")
	    public ContactDTO getById(@PathVariable Long id) {
	        return contactService.getById(id);
	    }

	    // UPDATE
	    @PutMapping("/{id}")
	    public ContactDTO update(@PathVariable Long id, @RequestBody ContactDTO dto) {
	        return contactService.update(id, dto);
	    }

	    // DELETE
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        contactService.delete(id);
	    }

	    // SEARCH
	    @GetMapping("/search")
	    public List<ContactDTO> search(@RequestParam String q) {
	        return contactService.search(q);
	    }

	    // FAVOURITES
	    @GetMapping("/favourites")
	    public List<ContactDTO> favourites(@RequestParam Long userId) {
	        Users user = userService.getById(userId);
	        return contactService.getFavourites(user);
	    }

	    // TOGGLE FAVOURITE
	    @PatchMapping("/{id}/favourite")
	    public ContactDTO toggle(@PathVariable Long id) {
	        return contactService.toggleFavourite(id);
	    }
}
