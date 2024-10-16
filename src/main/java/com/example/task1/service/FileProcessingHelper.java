package com.example.task1.service;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.StringReader;

import com.example.task1.model.GeoClass;

public class FileProcessingHelper {
    
    public static List<GeoClass> convertCSVtoDomainObjects(InputStream istream) throws IOException {
        StringBuilder normilizedData = normilizeData(istream);
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
        return geoClasses;
    }

    private static StringBuilder normilizeData(InputStream input) throws IOException {
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
