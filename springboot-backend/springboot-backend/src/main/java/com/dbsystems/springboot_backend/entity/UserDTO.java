package com.dbsystems.springboot_backend.entity;

import java.time.LocalDate;

public class UserDTO {
    public int userID;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public LocalDate dob;
    public String role;
    public int universityID;

    public UserDTO(User user) {
        this.userID = user.getUserID();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.dob = user.getDob();
        this.role = user.getRole();
        this.universityID = user.getUniversityID();
    }
}
