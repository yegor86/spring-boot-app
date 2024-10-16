package com.example.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.task1.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    
}
