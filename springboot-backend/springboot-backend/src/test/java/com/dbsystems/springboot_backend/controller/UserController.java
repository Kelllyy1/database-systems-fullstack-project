package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.dao.UserRepository;
import com.dbsystems.springboot_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //  GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    //  GET one user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        return user.<ResponseEntity<Object>>map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }

    //  POST (create new user)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    //  PUT (update user)
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> existing = userRepository.findById(id);

        if (existing.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = existing.get();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setDob(updatedUser.getDob());
        user.setPassword(updatedUser.getPassword());
        user.setUniversity(updatedUser.getUniversity());

        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    //  DELETE (remove user)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(404).body("User not found");
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // returns 204 No Content
    }
}