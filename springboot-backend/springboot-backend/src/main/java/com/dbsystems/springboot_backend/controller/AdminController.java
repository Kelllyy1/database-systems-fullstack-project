package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.dao.UserRepository;
import com.dbsystems.springboot_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    //  Update a user's role — manually promote to RSO Admin or SuperAdmin
    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(
            @PathVariable int id,
            @RequestParam String role
    ) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        User user = userOpt.get();
        user.setRole(role); // "Student", "RSO Admin", "SuperAdmin"
        userRepository.save(user);

        return ResponseEntity.ok("User role updated to: " + role);
    }
}
