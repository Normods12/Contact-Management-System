package com.contacts.Controller;

import com.contacts.Dto.StatsResponseDto;
import com.contacts.Dto.UsersResponseDto;
import com.contacts.Service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UsersResponseDto>> getAllUsers(){
        log.info("Received request to get all users");
        List<UsersResponseDto> usersResponseDto = adminService.getAllUsers();
        log.info("Fetched all Users");
        return new ResponseEntity<>(usersResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<> deleteUser(@PathVariable Long id){
        log.info("Received request to delete user {}", id);
        adminService.deleteUser(id);
        log.info("Deleted user {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponseDto> displayStats(){
        log.info("Received request to display stats");
        StatsResponseDto dto = adminService.displayStats();
        log.info("Fetched stats");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
