package com.contacts.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.contacts.Entity.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {



    Users findByUsername(String username);
}
