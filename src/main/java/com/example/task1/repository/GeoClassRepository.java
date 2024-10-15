package com.example.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.task1.model.GeoClass;

public interface GeoClassRepository extends JpaRepository<GeoClass, Long> {
    
}
