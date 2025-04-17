package com.dbsystems.springboot_backend.dao;

import com.dbsystems.springboot_backend.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
}
