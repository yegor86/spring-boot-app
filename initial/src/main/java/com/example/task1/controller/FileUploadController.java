package com.example.task1.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.task1.service.FileProcessingService;

@RestController
public class FileUploadController {
	
	@Autowired
	private FileProcessingService fileProcessingService;

	private ConcurrentHashMap<String, CompletableFuture<String>> fileStatuses;
	
	public FileUploadController() {
		this.fileStatuses = new ConcurrentHashMap<>();
	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
		modelMap.addAttribute("file", file);
        
		fileStatuses.put(file.getOriginalFilename(), fileProcessingService.processFileAsync(file));
        return "File " + file.getOriginalFilename() + " upload started, processing in the background.";
	}

	@GetMapping("/status/{fileName}")
    public ResponseEntity<String> checkStatus(@PathVariable String fileName) {
        boolean uploaded = fileStatuses.containsKey(fileName);
		if (!uploaded) {
			return ResponseEntity.ok("File not found: " + fileName);	
		}
		
		String status = fileStatuses.get(fileName).isDone() ? "COMPLETE" : "IN_PROGRESS";
		return ResponseEntity.ok("File processing status for: " + fileName + " is " + status);
    }
}
