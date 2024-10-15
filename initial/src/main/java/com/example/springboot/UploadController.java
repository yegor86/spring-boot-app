package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadController {

	@GetMapping("/upload")
	public String upload() {
		return "Drop files here...";
	}
}
