package com.contacts.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contacts.Entity.Contact;
import com.contacts.Entity.Users;

public interface ContactRepo extends JpaRepository<Contact, Long>{

	List<Contact> findByUserId(Long userId);
	List<Contact> findByUser(Users user);
	List<Contact> findByUserAndIsFavouriteTrue(Users user);
	List<Contact> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContaining(
		        String name, String email, String phone );

    List<Contact> findByUserIdAndIsFavouriteTrue(Long userId);
}
