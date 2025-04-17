package com.dbsystems.springboot_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationID;

    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
}
