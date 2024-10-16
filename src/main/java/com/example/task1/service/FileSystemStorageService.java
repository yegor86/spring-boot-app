package com.example.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService {

    private final Path dirLocation;

    public FileSystemStorageService(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
        		                .toAbsolutePath()
        		                .normalize();
    }
    
    public String saveFile(MultipartFile file) throws IOException {
		
        String fileName = file.getOriginalFilename();
        Path dfile = this.dirLocation.resolve(fileName);
        Files.copy(file.getInputStream(), dfile, StandardCopyOption.REPLACE_EXISTING);
    
        return dfile.toString();
	}

    public Resource loadFile(String fileName) throws FileNotFoundException {
		try {
	        Path file = this.dirLocation.resolve(fileName).normalize();
	        Resource resource = new UrlResource(file.toUri());

	        if (resource.exists() || resource.isReadable()) {
	            return resource;
	        }
	        throw new FileNotFoundException("Could not find file");
	    } catch (MalformedURLException e) {
			throw new FileNotFoundException("Could not download file");
	    }
	}
}
