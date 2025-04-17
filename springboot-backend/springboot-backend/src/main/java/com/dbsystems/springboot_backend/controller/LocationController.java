package com.dbsystems.springboot_backend.controller;

import com.dbsystems.springboot_backend.dao.LocationRepository;
import com.dbsystems.springboot_backend.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    // GET all
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

    // GET one
    @GetMapping("/{id}")
    public ResponseEntity<Object> getLocationById(@PathVariable Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Location not found"));
    }

    // POST (create)
    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        return ResponseEntity.ok(locationRepository.save(location));
    }

    // PUT (update)
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLocation(@PathVariable Integer id, @RequestBody Location updated) {
        Optional<Location> existing = locationRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(404).body("Location not found");
        }

        Location location = existing.get();
        location.setName(updated.getName());
        location.setAddress(updated.getAddress());
        location.setCity(updated.getCity());
        location.setState(updated.getState());
        location.setZip(updated.getZip());

        return ResponseEntity.ok(locationRepository.save(location));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLocation(@PathVariable Integer id) {
        if (!locationRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Location not found");
        }

        locationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
