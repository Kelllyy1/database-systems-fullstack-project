// Iteration 2 - allowed user to enter a null
package com.dbsystems.springboot_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;

    @Column(name = "first_name")  
    private String firstName;

    @Column(name = "last_name")  
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    @ManyToOne
    @JoinColumn(name = "universityID", nullable = false)
    @JsonBackReference
    private University university;

    public int getUniversityID() {
        return university != null ? university.getUniversityID() : -1;
    }
    
}
