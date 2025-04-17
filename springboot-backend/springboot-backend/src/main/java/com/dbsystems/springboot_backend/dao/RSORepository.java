package com.dbsystems.springboot_backend.dao;

import com.dbsystems.springboot_backend.entity.RSO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RSORepository extends JpaRepository<RSO, Integer> {
    List<RSO> findByUniversityUniversityID(int universityID);
    List<RSO> findByAdmin_UserID(int userID);
    List<RSO> findByApprovedFalse(); // For superadmin pending approval view
}
