package com.dbsystems.springboot_backend.dao;

import com.dbsystems.springboot_backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    // I can add custom query methods here later
}
