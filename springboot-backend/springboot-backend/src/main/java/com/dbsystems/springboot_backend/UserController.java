package main.java.com.dbsystems.springboot_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/")
    public String home() {
        return "Hello, Spring Boot is working!";
    }
}
