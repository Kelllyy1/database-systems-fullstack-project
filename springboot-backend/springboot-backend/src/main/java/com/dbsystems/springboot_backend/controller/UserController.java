package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.entity.RSO;
import com.dbsystems.springboot_backend.entity.StudentRSO;
import com.dbsystems.springboot_backend.entity.User;
import com.dbsystems.springboot_backend.dao.StudentRSORepository;
import com.dbsystems.springboot_backend.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRSORepository studentRSORepository;

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET one user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    // PUT - Update a user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setEmail(userDetails.getEmail());
                    user.setPhoneNumber(userDetails.getPhoneNumber());
                    user.setPassword(userDetails.getPassword());
                    user.setDob(userDetails.getDob());
                    user.setUniversity(userDetails.getUniversity());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Remove a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET RSOs that a user has joined
    @GetMapping("/{userId}/joined-rsos")
    public ResponseEntity<List<RSO>> getJoinedRSOs(@PathVariable int userId) {
        List<RSO> rsos = studentRSORepository.findByUser_UserID(userId)
                .stream()
                .map(StudentRSO::getRso)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rsos);
    }
}
