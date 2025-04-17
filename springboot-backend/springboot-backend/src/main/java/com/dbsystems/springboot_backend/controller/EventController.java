package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.dao.*;
import com.dbsystems.springboot_backend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    @Autowired private EventRepository eventRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private LocationRepository locationRepository;
    @Autowired private RSORepository rsoRepository;
    @Autowired private StudentRSORepository studentRSORepository;

    // GET all events (admin/super)
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventRepository.findAll());
    }

    // GET visible events for a student
    @GetMapping("/student/{userID}")
    public ResponseEntity<List<Event>> getVisibleEventsForStudent(@PathVariable int userID) {
        Optional<User> userOpt = userRepository.findById(userID);
        if (userOpt.isEmpty()) return ResponseEntity.status(404).build();

        User user = userOpt.get();
        int universityID = user.getUniversity().getUniversityID();

        List<Event> all = eventRepository.findAll();
        List<Event> visible = all.stream().filter(event -> {
            String visibility = event.getVisibility().toLowerCase();
            if (visibility.equals("public")) return event.isApproved(); // only approved public events
            if (visibility.equals("private")) {
                return event.getCreatedBy().getUniversity().getUniversityID() == universityID;
            }
            if (visibility.equals("rso") && event.getRso() != null) {
                return studentRSORepository.existsByUser_UserIDAndRso_RsoID(userID, event.getRso().getRsoID());
            }
            return false;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(visible);
    }

    // POST: Create Event
    @PostMapping
    public ResponseEntity<Object> createEvent(
        @RequestParam Integer createdById,
        @RequestParam Integer approvedById,
        @RequestParam Integer locationId,
        @RequestBody Event event
    ) {
        Optional<User> creator = userRepository.findById(createdById);
        Optional<User> approver = userRepository.findById(approvedById);
        Optional<Location> location = locationRepository.findById(locationId);

        if (creator.isEmpty() || approver.isEmpty() || location.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid creator, approver, or location ID");
        }

        event.setCreatedBy(creator.get());
        event.setApprovedBy(approver.get());
        event.setLocation(location.get());

        if (event.getRso() != null && event.getRso().getRsoID() != 0) {
            rsoRepository.findById(event.getRso().getRsoID()).ifPresent(event::setRso);
        }

        // Only approve instantly if not public
        if (event.getVisibility().equalsIgnoreCase("public")) {
            event.setApproved(false); // Require super approval for public events
        } else {
            event.setApproved(true); // Private & RSO events are auto-approved
        }

        return ResponseEntity.ok(eventRepository.save(event));
    }

    // PUT: Approve public event
    @PutMapping("/{id}/approve")
    public ResponseEntity<Object> approvePublicEvent(@PathVariable Integer id, @RequestParam Integer approvedById) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        Optional<User> approverOpt = userRepository.findById(approvedById);

        if (eventOpt.isEmpty() || approverOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Event or user not found");
        }

        Event event = eventOpt.get();
        if (!event.getVisibility().equalsIgnoreCase("public")) {
            return ResponseEntity.badRequest().body("Only public events require approval");
        }

        event.setApproved(true);
        event.setApprovedBy(approverOpt.get());
        return ResponseEntity.ok(eventRepository.save(event));
    }

    // GET: Admin-specific filtered view
    @GetMapping("/admin/{userID}")
    public ResponseEntity<List<Event>> getVisibleEventsForAdmin(@PathVariable int userID) {
        Optional<User> userOpt = userRepository.findById(userID);
        if (userOpt.isEmpty()) return ResponseEntity.status(404).build();

        User user = userOpt.get();
        int universityID = user.getUniversity().getUniversityID();

        List<Event> filtered = eventRepository.findAll().stream()
            .filter(e -> {
                if (e.getVisibility().equalsIgnoreCase("public")) return true;
                if (e.getVisibility().equalsIgnoreCase("private")) {
                    return e.getCreatedBy().getUniversity().getUniversityID() == universityID;
                }
                if (e.getVisibility().equalsIgnoreCase("rso") && e.getRso() != null) {
                    return e.getRso().getUniversity().getUniversityID() == universityID;
                }
                return false;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(filtered);
    }

    //  Defined this above the `/{id}` route
    @GetMapping("/pending-public")
    public ResponseEntity<List<Event>> getPendingPublicEvents() {
        List<Event> pendingPublics = eventRepository.findAll().stream()
            .filter(e -> e.getVisibility().equalsIgnoreCase("public") && !e.isApproved())
            .collect(Collectors.toList());
        return ResponseEntity.ok(pendingPublics);
    }


    //  GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable Integer id) {
        return eventRepository.findById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Event not found"));
    }

    // PUT: Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable Integer id, @RequestBody Event updatedEvent) {
        Optional<Event> existing = eventRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.status(404).body("Event not found");

        Event event = existing.get();
        event.setName(updatedEvent.getName());
        event.setDescription(updatedEvent.getDescription());
        event.setDate(updatedEvent.getDate());
        event.setContactEmail(updatedEvent.getContactEmail());
        event.setContactPhone(updatedEvent.getContactPhone());
        event.setType(updatedEvent.getType());
        event.setVisibility(updatedEvent.getVisibility());

        return ResponseEntity.ok(eventRepository.save(event));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Integer id) {
        if (!eventRepository.existsById(id)) return ResponseEntity.status(404).body("Event not found");

        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

// GET: Approved public events (for Landing Page)
    @GetMapping("/public")
    public ResponseEntity<List<Event>> getApprovedPublicEvents() {
        List<Event> approvedPublics = eventRepository.findAll().stream()
            .filter(e -> e.getVisibility().equalsIgnoreCase("public") && e.isApproved())
            .collect(Collectors.toList());

        return ResponseEntity.ok(approvedPublics);
    }

}
