package com.dbsystems.springboot_backend.dao;

import com.dbsystems.springboot_backend.entity.StudentRSO;
import com.dbsystems.springboot_backend.entity.StudentRSOId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRSORepository extends JpaRepository<StudentRSO, StudentRSOId> {
    int countByRsoRsoID(int rsoID);
    List<StudentRSO> findByIdUserID(int userID);       
    List<StudentRSO> findByRsoRsoID(int rsoID);         
    List<StudentRSO> findByUser_UserID(int userID);     
    boolean existsByUser_UserIDAndRso_RsoID(int userID, int rsoID);

}
