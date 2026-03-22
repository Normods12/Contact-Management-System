package com.contacts.Dto;


import lombok.Data;

@Data
public class ContactResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Boolean isFavourite;
}
