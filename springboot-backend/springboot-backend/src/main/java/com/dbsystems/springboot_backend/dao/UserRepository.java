package com.dbsystems.springboot_backend.dao;

import com.dbsystems.springboot_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    // Used to find students from the same university with matching domain
    List<User> findByEmailEndingWithAndRoleAndUniversityUniversityID(String domain, String role, int universityID);
    List<User> findByEmailEndingWithAndRole(String domain, String role);
}