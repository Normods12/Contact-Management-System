package com.contacts.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.service.registry.HttpServiceGroupConfigurer;

import java.time.LocalDateTime;

@Entity
@Table(name = "Contacts")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Contact
{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = true)
    private String lastName;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = true)
    private String email;
    @Column(nullable = true)
    private String address;
    @Column(nullable = true)
    private String company;
    @Column(nullable = true)
    private String notes;
    @Column(nullable = true)
    private Boolean isFavourite;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    @JsonIgnore
    private Group group;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;


    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


}
