package com.dbsystems.springboot_backend.dao;

import com.dbsystems.springboot_backend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
