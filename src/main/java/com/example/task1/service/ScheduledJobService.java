package com.example.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.example.task1.model.GeoClass;
import com.example.task1.model.Job;
import com.example.task1.model.JobGeoClass;
import com.example.task1.repository.GeoClassRepository;
import com.example.task1.repository.JobGeoClassRepository;
import com.example.task1.repository.JobRepository;

@Component
public class ScheduledJobService {
    
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobGeoClassRepository jobGeoClassRepository;

    @Autowired
    private GeoClassRepository geoClassRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Scheduled(cron = "*/10 * * * * *")
    public void execute() throws FileNotFoundException {
        
        Optional<Job> record = jobRepository.findOldestJobExcludingStatuses(Arrays.asList("Running", "Complete", "Failed"));
        if (record.isEmpty()) {
            return;
        }
        Job job = record.get();
        job.start();
        jobRepository.save(job); // use optimistic locking in Prod 

        Resource resource = fileSystemStorageService.loadFile(job.getFilepath());
        try {
            List<GeoClass> geoClasses = FileProcessingHelper.convertCSVtoDomainObjects(resource.getInputStream());
            
            // Should be saved within a single transactional method, i.e. with @Transactional annotatiion to avoid inconsistency    
            job.end();
            geoClassRepository.saveAll(geoClasses);
            jobRepository.save(job);

            List<JobGeoClass> jobGeoClasses = new ArrayList<>();
            for (GeoClass geoclass: geoClasses) {
                jobGeoClasses.add(new JobGeoClass(job.getId(), geoclass.getId()));
            }
            jobGeoClassRepository.saveAll(jobGeoClasses);
            
        } catch (IOException e) {
            job.setStatus("Failed");
            jobRepository.save(job);
            return;
        }
    }
}