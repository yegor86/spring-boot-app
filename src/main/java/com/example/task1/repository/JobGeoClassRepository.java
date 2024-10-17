package com.example.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task1.model.JobGeoClass;

public interface JobGeoClassRepository extends JpaRepository<JobGeoClass, Long> {
    
}
