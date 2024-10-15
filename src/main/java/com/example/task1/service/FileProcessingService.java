package com.example.task1.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.task1.model.GeoClass;
import com.example.task1.repository.GeoClassRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.StringReader;

@Service
public class FileProcessingService {

    @Autowired
    private GeoClassRepository geoClassRepository;

    @Async
    public CompletableFuture<String> processFileAsync(MultipartFile file) {
        try {

            System.out.println("Processing file: " + file.getOriginalFilename());

            StringBuilder normilizedData = normilizeData(file.getInputStream());
            CSVParser csvParser = CSVFormat.Builder.create()
                .setDelimiter(",")
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build()
                .parse(new StringReader(normilizedData.toString()));
            
            csvParser.iterator().next();
            
            List<GeoClass> geoClasses = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                
                // Read Section name
                String sectionName = record.get(0);
                // Read Class name, code pairs 
                for (int i = 0; i + 2 < record.size(); i += 2) {
                    String className = record.get(i + 1);
                    String classCode = record.get(i + 2);
                    geoClasses.add(new GeoClass(className, classCode, sectionName));
                }
            }

            geoClassRepository.saveAll(geoClasses);

            // Simulating long-running task
            Thread.sleep(5000);

            System.out.println("File processing completed: " + file.getOriginalFilename());
            return CompletableFuture.completedFuture("File processed successfully");

        } catch (IOException | InterruptedException e) {
            // Handle exception properly
            return CompletableFuture.completedFuture("Error processing file: " + e.getMessage());
        }
    }

    private StringBuilder normilizeData(InputStream input) throws IOException {
        StringBuilder normalizedData = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Replace semicolons and pipes with commas
                line = line
                    .replace(";", ",")
                    .replace("|", ",");
                normalizedData.append(line).append("\n");
            }
        }
        return normalizedData;
    }
}
