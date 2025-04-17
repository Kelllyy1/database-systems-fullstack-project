package com.dbsystems.springboot_backend.controller.security;

import com.dbsystems.springboot_backend.dao.UserRepository;
import com.dbsystems.springboot_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class SimpleAuthController {

    @Autowired
    private UserRepository userRepository;

    // SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password are required.");
            }

            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(409).body("A user with this email already exists.");
            }

            // Default role to student if not set
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("student");
            }

            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error during signup: " + e.getMessage());
        }
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password are required.");
            }

            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body("Email not found.");
            }

            User user = userOpt.get();
            if (!user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.status(401).body("Incorrect password.");
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Login error: " + e.getMessage());
        }
    }

    // DTO for login request
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
