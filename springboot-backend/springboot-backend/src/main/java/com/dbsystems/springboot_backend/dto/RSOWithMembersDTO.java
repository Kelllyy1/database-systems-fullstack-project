package com.dbsystems.springboot_backend.dto;

import com.dbsystems.springboot_backend.entity.University;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RSOWithMembersDTO {
    private int rsoID;
    private String name;
    private University university;
    private int memberCount;
}
