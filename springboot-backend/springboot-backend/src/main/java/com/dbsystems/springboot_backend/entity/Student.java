package com.dbsystems.springboot_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "studentrso")  // Matches your MySQL table name
@Data
public class Student {

    @Id
    @Column(name = "userID")
    private Integer userID;

    @OneToOne(cascade = CascadeType.PERSIST)  // Prevents full cascade (safer for prod)
    @MapsId
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rsoID", nullable = false)
    private RSO rso;
}
