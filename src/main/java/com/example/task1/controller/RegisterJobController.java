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

import com.example.task1.model.GeoClass;
import com.example.task1.model.Job;
import com.example.task1.model.RegisterJobResult;
import com.example.task1.service.FileProcessingService;
import com.example.task1.service.FileSystemStorageService;
import com.example.task1.service.JobRegistrationService;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/v1")
public class RegisterJobController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterJobController.class);
	
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
	public ResponseEntity<RegisterJobResult> submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
		
		if (!supportedFormats.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
			logger.error("Not supported file format: " + FilenameUtils.getExtension(file.getOriginalFilename()));
			return ResponseEntity.badRequest().body(new RegisterJobResult(null, "Failed"));
		}
		
		try {
			String localPath = fileSystemStorageService.saveFile(file);
			Long jobId = jobRegistrationService.registerJobAndReturnId(new Job(localPath));
			return ResponseEntity.ok(new RegisterJobResult(jobId, "Pending"));
		} catch (IOException e) {
			logger.error("Register job failed", e);
			return ResponseEntity.internalServerError().body(new RegisterJobResult(null, "Failed"));
		}
	}

	@GetMapping("/job/status/{jobId}")
    public ResponseEntity<List<GeoClass>> checkStatus(@PathVariable Long jobId) {
		List<GeoClass> geoClasses = jobRegistrationService.findGeoClassesByJobId(jobId);
		return ResponseEntity.ok(geoClasses);
    }
}
