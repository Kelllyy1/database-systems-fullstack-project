package com.dbsystems.springboot_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dbsystems.springboot_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // custom queries (if needed)
}
