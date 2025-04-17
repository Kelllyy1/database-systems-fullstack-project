package com.dbsystems.springboot_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "studentrso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRSO {

    @EmbeddedId
    private StudentRSOId id;

@ManyToOne
@MapsId("userID")
@JoinColumn(name = "userID")
@JsonBackReference(value = "studentrso-user")
private User user;

@ManyToOne
@MapsId("rsoID")
@JoinColumn(name = "rsoID")
@JsonBackReference(value = "studentrso-rso")
private RSO rso;


}
