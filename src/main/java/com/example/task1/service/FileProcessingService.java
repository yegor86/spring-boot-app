package com.example.task1.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.task1.model.GeoClass;
import com.example.task1.repository.GeoClassRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(FileProcessingService.class);

    @Autowired
    private GeoClassRepository geoClassRepository;

    @Async
    public CompletableFuture<String> processFileAsync(MultipartFile file) {
        try {

            logger.info("Processing file: {}", file.getOriginalFilename());

            List<GeoClass> geoClasses = FileProcessingHelper.convertCSVtoDomainObjects(file.getInputStream());

            geoClassRepository.saveAll(geoClasses);

            // Simulating long-running task
            Thread.sleep(5000);

            logger.info("File processing completed: {}", file.getOriginalFilename());
            return CompletableFuture.completedFuture("File processed successfully");

        } catch (IOException | InterruptedException e) {
            // Handle exception properly
            return CompletableFuture.completedFuture("Error processing file: " + e.getMessage());
        }
    }
}
