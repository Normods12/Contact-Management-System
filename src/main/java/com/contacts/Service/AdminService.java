package com.contacts.Service;

import com.contacts.Dto.StatsResponseDto;
import com.contacts.Dto.UsersResponseDto;
import com.contacts.Entity.Users;
import com.contacts.Repository.ContactRepo;
import com.contacts.Repository.GroupRepository;
import com.contacts.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    GroupRepository groupRepo;

    public List<UsersResponseDto> getAllUsers() {
        log.info("Fetching users from database...");
        List<Users> users = userRepo.findAll();
        log.info("Fetching users from database complete.");
        List<UsersResponseDto> usersList = new ArrayList<>();
        for (Users user : users) {
            UsersResponseDto usersDto = new UsersResponseDto();
            usersDto.setId(user.getId());
            usersDto.setFirstName(user.getFirstName());
            usersDto.setEmail(user.getEmail());
            usersList.add(usersDto);

        }
        return usersList;
    }

    public void deleteUser(long id) {
        log.debug("Checking if user Exists");
        Users user = userRepo.findById((int)id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        userRepo.delete(user);
    }

    public StatsResponseDto displayStats() {
        log.info("Fetching stats from database...");
        StatsResponseDto statsDto = new StatsResponseDto();
        log.info("Fetching User Count");
        statsDto.setTotalUsers(userRepo.count());
        log.info("Fetching Contact Count");
        statsDto.setTotalContacts(contactRepo.count());
           log.info("Fetching Group Count");
           statsDto.setGroupCount(groupRepo.count());

           return statsDto;
    }





}
