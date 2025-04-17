package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.dao.RSORepository;
// import com.dbsystems.springboot_backend.dao.StudentRSORepository;
import com.dbsystems.springboot_backend.dao.StudentRepository;
import com.dbsystems.springboot_backend.dao.UserRepository;
import com.dbsystems.springboot_backend.entity.RSO;
import com.dbsystems.springboot_backend.entity.Student;
import com.dbsystems.springboot_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RSORepository rsoRepository;

    // GET all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    // GET one student
    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Student not found"));
    }

    // POST student (by linking to user + RSO)
    @PostMapping
    public ResponseEntity<Object> createStudent(
            @RequestParam Integer userId,
            @RequestParam Integer rsoId
    ) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<RSO> rsoOpt = rsoRepository.findById(rsoId);

        if (userOpt.isEmpty() || rsoOpt.isEmpty()) {
            return ResponseEntity.status(400).body("Invalid user ID or RSO ID");
        }

        Student student = new Student();
        student.setUser(userOpt.get());
        student.setRso(rsoOpt.get());

        return ResponseEntity.ok(studentRepository.save(student));
    }

    // DELETE student
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Student not found");
        }

        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
