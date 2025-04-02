package com.dbsystems.springboot_backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    private String firstName;

    private String lastName;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private LocalDate dob;

    private String password;

    private Integer universityID;
}
