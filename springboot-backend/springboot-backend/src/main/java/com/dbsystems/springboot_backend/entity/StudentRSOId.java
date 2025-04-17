package com.dbsystems.springboot_backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRSOId implements Serializable {
    private int userID;
    private int rsoID;
}
