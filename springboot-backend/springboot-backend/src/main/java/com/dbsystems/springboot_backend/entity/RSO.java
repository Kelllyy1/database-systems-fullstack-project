package com.dbsystems.springboot_backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rso")
@Data
public class RSO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsoID")
    private int rsoID;

    @Column(name = "name")
    private String name;

    @Column(name = "approved")
    private Boolean approved = false; // Superadmin approval status
    

    public boolean isApproved() {
        return Boolean.TRUE.equals(this.approved);
    }
    

public void setApproved(boolean approved) {
    this.approved = approved;
}


    @ManyToOne
    @JoinColumn(name = "universityID", nullable = false)
    private University university;

    @ManyToOne
    @JoinColumn(name = "adminID")
    private User admin;

    @OneToMany(mappedBy = "rso")
    @JsonIgnore
    private List<StudentRSO> studentRSOMembers;

    @OneToMany(mappedBy = "rso")
    @JsonManagedReference(value = "studentrso-rso")
    private List<StudentRSO> members;

    public int getMemberCount() {
        return studentRSOMembers != null ? studentRSOMembers.size() : 0;
    }
}