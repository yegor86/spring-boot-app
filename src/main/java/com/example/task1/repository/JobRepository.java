package com.example.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

import com.example.task1.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    
    @Query("SELECT j FROM Job j WHERE j.status NOT IN (:statuses) AND j.startTime is NULL ORDER BY j.startTime ASC LIMIT 1")
    Optional<Job> findOldestJobExcludingStatuses(@Param("statuses") List<String> statuses);

    @Modifying
    @Query("UPDATE Job j SET j.status = :newStatus WHERE j.id = :jobId AND j.status = :currentStatus")
    int updateJobStatusIf(@Param("jobId") Long jobId, @Param("currentStatus") String currentStatus, @Param("newStatus") String newStatus);
}
