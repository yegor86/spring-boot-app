package com.example.task1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.task1.model.Job;
import com.example.task1.repository.JobRepository;

@Service
public class JobRegistrationService {
    
    @Autowired
    private JobRepository jobRepository;

    public Long registerJobAndReturnId(Job job) {

        Job result = jobRepository.save(job);
        return result.getId();
    }

    public Job findJobByID(Long jobId) {

        Optional<Job> job = jobRepository.findById(jobId);
        return job.orElse(null);
    }
}
