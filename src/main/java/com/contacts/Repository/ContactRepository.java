package com.contacts.Repository;


import com.contacts.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByGroupId(Long groupId);
}