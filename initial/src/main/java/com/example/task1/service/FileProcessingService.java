package com.example.task1.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileProcessingService {
    
    @Async
    public CompletableFuture<String> processFileAsync(MultipartFile file) {
        try {
            System.out.println("Processing file: " + file.getOriginalFilename());

            // Simulate processing
            byte[] fileContent = file.getBytes();
            Thread.sleep(5000); // Simulating long-running task

            System.out.println("File processing completed: " + file.getOriginalFilename());
            return CompletableFuture.completedFuture("File processed successfully");

        } catch (IOException | InterruptedException e) {
            // Handle exception properly
            return CompletableFuture.completedFuture("Error processing file: " + e.getMessage());
        }
    }
}
