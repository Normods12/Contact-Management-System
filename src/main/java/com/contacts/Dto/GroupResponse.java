package com.contacts.Dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupResponse {
    private Long id;
    private String name;
    private String colorTag;
    private LocalDateTime createdDate;
}