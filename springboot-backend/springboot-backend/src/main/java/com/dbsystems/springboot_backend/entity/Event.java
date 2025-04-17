package com.dbsystems.springboot_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventID;

    private String name;
    private String description;
    private LocalDate date;
    private String contactEmail;
    private String contactPhone;
    private String type;
    private String visibility;

    @Column(name = "approved")
    @Builder.Default
    private boolean approved = false;    
    

    public boolean isApproved() {
        return Boolean.TRUE.equals(this.approved);
    }

    @ManyToOne
    @JoinColumn(name = "locationID")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @ManyToOne
    @JoinColumn(name = "rsoID")
    private RSO rso;
}
