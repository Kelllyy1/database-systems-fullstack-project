package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.dao.*;
import com.dbsystems.springboot_backend.dto.RSODTO;
import com.dbsystems.springboot_backend.dto.RSOWithMembersDTO;
import com.dbsystems.springboot_backend.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/rsos")
public class RSOController {

    @Autowired
    private RSORepository rsoRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRSORepository studentRSORepository;

    // Return only APPROVED RSOs with member count
    @GetMapping
    public ResponseEntity<List<RSOWithMembersDTO>> getAllRSOs() {
        List<RSO> rsos = rsoRepository.findAll()
            .stream()
            .filter(RSO::isApproved)
            .collect(Collectors.toList());

        List<RSOWithMembersDTO> dtoList = new ArrayList<>();
        for (RSO rso : rsos) {
            int count = studentRSORepository.countByRsoRsoID(rso.getRsoID());
            dtoList.add(new RSOWithMembersDTO(rso.getRsoID(), rso.getName(), rso.getUniversity(), count));
        }

        return ResponseEntity.ok(dtoList);
    }

    // Join an APPROVED RSO
    @PostMapping("/{rsoID}/join")
    public ResponseEntity<?> joinRSO(@PathVariable int rsoID, @RequestBody Map<String, Integer> payload) {
        int userID = payload.get("userID");
        Optional<User> userOpt = userRepository.findById(userID);
        Optional<RSO> rsoOpt = rsoRepository.findById(rsoID);

        if (userOpt.isEmpty() || rsoOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User or RSO not found.");
        }

        if (!rsoOpt.get().isApproved()) {
            return ResponseEntity.status(403).body("RSO has not been approved yet.");
        }

        StudentRSO studentRSO = new StudentRSO(new StudentRSOId(userID, rsoID), userOpt.get(), rsoOpt.get());
        studentRSORepository.save(studentRSO);

        return ResponseEntity.ok("Joined RSO.");
    }

    // Leave RSO
    @DeleteMapping("/{rsoID}/leave")
    public ResponseEntity<?> leaveRSO(@PathVariable int rsoID, @RequestParam int userID) {
        StudentRSOId id = new StudentRSOId(userID, rsoID);
        if (!studentRSORepository.existsById(id)) {
            return ResponseEntity.status(404).body("You are not a member of this RSO.");
        }

        studentRSORepository.deleteById(id);
        return ResponseEntity.ok("Left the RSO successfully.");
    }

    // Admin or Superadmin creates instantly APPROVED RSO
    @PostMapping
    public ResponseEntity<Object> createRSO(@RequestParam String name,
                                            @RequestParam Integer universityId,
                                            @RequestParam Integer userId) {
        Optional<University> uniOpt = universityRepository.findById(universityId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (uniOpt.isEmpty()) return ResponseEntity.status(400).body("Invalid university ID");
        if (userOpt.isEmpty() || (!userOpt.get().getRole().equals("admin") && !userOpt.get().getRole().equals("super"))) {
            return ResponseEntity.status(403).body("Only admin or super can create RSOs");
        }

        RSO rso = new RSO();
        rso.setName(name);
        rso.setUniversity(uniOpt.get());
        rso.setAdmin(userOpt.get());
        rso.setApproved(true); // instantly approved

        return ResponseEntity.ok(rsoRepository.save(rso));
    }

    // Student requests RSO creation (pending approval)
    // @PostMapping("/request")
    // public ResponseEntity<?> requestRSOCreation(@RequestParam String name, @RequestParam int userID) {
    //     Optional<User> userOpt = userRepository.findById(userID);
    //     if (userOpt.isEmpty()) return ResponseEntity.status(404).body("User not found");

    //     User user = userOpt.get();
    //     String domain = user.getEmail().substring(user.getEmail().indexOf("@"));
    //     List<User> matchingStudents = userRepository.findByEmailEndingWithAndRole(domain, "student");

    //     if (matchingStudents.size() < 5) {
    //         return ResponseEntity.status(400).body("At least 5 students with the same email domain are required.");
    //     }

    //     RSO rso = new RSO();
    //     rso.setName(name);
    //     rso.setUniversity(user.getUniversity());
    //     rso.setAdmin(user);
    //     rso.setApproved(false); // pending

    //     RSO saved = rsoRepository.save(rso);

    //     // Automatically add student to the RSO they requested
    //     StudentRSO studentRSO = new StudentRSO(new StudentRSOId(userID, saved.getRsoID()), user, saved);
    //     studentRSORepository.save(studentRSO);


    //     user.setRole("admin");
    //     userRepository.save(user);

    //     return ResponseEntity.ok(saved);
    // }
    @PostMapping("/request")
    public ResponseEntity<?> requestRSOCreation(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        int userID = (Integer) payload.get("userID");
        
        Optional<User> userOpt = userRepository.findById(userID);
        if (!userOpt.isPresent()) return ResponseEntity.status(404).body("User not found");
    
        User user = userOpt.get();
        String domain = user.getEmail().substring(user.getEmail().indexOf("@"));
        List<User> matchingStudents = userRepository.findByEmailEndingWithAndRole(domain, "student");
    
        if (matchingStudents.size() < 5) {
            return ResponseEntity.status(400).body("At least 5 students with the same email domain are required.");
        }
    
        RSO rso = new RSO();
        rso.setName(name);
        rso.setUniversity(user.getUniversity());
        rso.setAdmin(user);
        rso.setApproved(false);  // Pending approval
    
        rsoRepository.save(rso);
    
        return ResponseEntity.ok("RSO request submitted for approval");
    }
    
    

    // Superadmin: View RSOs pending approval
    @GetMapping("/pending")
    public ResponseEntity<List<RSO>> getPendingRSOs() {
        return ResponseEntity.ok(rsoRepository.findByApprovedFalse());
    }

    // Superadmin: Approve an RSO (fix PUT route)
    @PutMapping("/{rsoID}/approve")
    public ResponseEntity<?> approveRSO(@PathVariable int rsoID) {
        Optional<RSO> optionalRSO = rsoRepository.findById(rsoID);
        if (optionalRSO.isPresent()) {
            RSO rso = optionalRSO.get();
            rso.setApproved(true);
            rsoRepository.save(rso);
            return ResponseEntity.ok("RSO approved successfully");
        }
        return ResponseEntity.status(404).body("RSO not found");
    }

    // Get RSOs joined by a user
    @GetMapping("/users/{userId}/joined-rsos")
    public ResponseEntity<List<RSODTO>> getJoinedRSOs(@PathVariable int userId) {
        List<RSODTO> rsos = studentRSORepository.findByUser_UserID(userId)
            .stream()
            .map(rso -> new RSODTO()) // I can map real values here later
            .collect(Collectors.toList());

        return ResponseEntity.ok(rsos);
    }

    // Admin or Superadmin: View RSOs they created
    @GetMapping("/owned-by/{userID}")
    public ResponseEntity<List<RSOWithMembersDTO>> getRSOsOwnedByUser(@PathVariable int userID) {
        // Get all RSOs created by this admin, pending or approved
        List<RSO> rsos = rsoRepository.findByAdmin_UserID(userID);
        
        // Convert to DTO that includes member count.
        List<RSOWithMembersDTO> dtoList = new ArrayList<>();
        for (RSO rso : rsos) {
            int count = studentRSORepository.countByRsoRsoID(rso.getRsoID());
            dtoList.add(new RSOWithMembersDTO(rso.getRsoID(), rso.getName(), rso.getUniversity(), count));
        }
        
        return ResponseEntity.ok(dtoList);
    }

}
