package com.dbsystems.springboot_backend;

import com.dbsystems.springboot_backend.dao.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {

    @Autowired
    protected UniversityRepository universityRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RSORepository rsoRepository;

    @Autowired
    protected StudentRepository studentRepository;

    @AfterEach
    @Transactional
    public void cleanDatabase() {
        studentRepository.deleteAll();   // Depends on User and RSO
        rsoRepository.deleteAll();       // Depends on User and University
        userRepository.deleteAll();      // Depends on University
        universityRepository.deleteAll();
    }
}
