package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.dao.UniversityRepository;
import com.dbsystems.springboot_backend.dao.RSORepository;
import com.dbsystems.springboot_backend.entity.RSO;
import com.dbsystems.springboot_backend.entity.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private RSORepository rsoRepository;

    // Get all universities
    @GetMapping
    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    // Get university by ID
    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable int id) {
        return universityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new university
    @PostMapping
    public University createUniversity(@RequestBody University university) {
        return universityRepository.save(university);
    }

    // Update university
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable int id, @RequestBody University updated) {
        return universityRepository.findById(id).map(university -> {
            university.setName(updated.getName());
            university.setLocation(updated.getLocation());
            university.setNumStudents(updated.getNumStudents());
            return ResponseEntity.ok(universityRepository.save(university));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete university
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUniversity(@PathVariable int id) {
        return universityRepository.findById(id).map(university -> {
            universityRepository.delete(university);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{universityID}/rsos")
    public List<RSO> getRSOsByUniversity(@PathVariable int universityID) {
        return rsoRepository.findByUniversityUniversityID(universityID);
    }
    

}
