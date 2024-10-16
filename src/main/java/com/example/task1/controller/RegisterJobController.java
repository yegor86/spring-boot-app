package com.example.task1.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

import com.example.task1.model.Job;
import com.example.task1.service.FileSystemStorageService;
import com.example.task1.service.JobRegistrationService;

import org.apache.commons.io.FilenameUtils;


@RestController
@RequestMapping("/api/v1")
public class RegisterJobController {
	
	@Autowired
	private JobRegistrationService jobRegistrationService;

	@Autowired
	private FileSystemStorageService fileSystemStorageService;

	private List<String> supportedFormats = Arrays.asList(
		"csv"
		// "xslx"
	);
	
	public RegisterJobController() {
	
	}

	@RequestMapping(value = "/job/register", method = RequestMethod.POST)
	public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
		
		if (!supportedFormats.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
			return "Not supported file format: " + FilenameUtils.getExtension(file.getOriginalFilename());
		}
		
		try {
			String localPath = fileSystemStorageService.saveFile(file);
			Long jobId = jobRegistrationService.registerJobAndReturnId(new Job(localPath));
        	return "Job ID: " + jobId;
		} catch (IOException e) {
			return "Job Registration failed:";
		}
	}

	@GetMapping("/job/status/{jobId}")
    public ResponseEntity<Job> checkStatus(@PathVariable Long jobId) {
		Job job = jobRegistrationService.findJobByID(jobId);
		if (job == null) {
			return ResponseEntity.notFound().build();
		}
        return ResponseEntity.ok(job);
    }
}
