package com.example.chicken.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.common.service.S3Provider;
import com.example.chicken.dto.S3FileNameDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
	private final S3Provider s3Provider;

	@PostMapping("/upload-url")
	public ResponseEntity<Map<String, String>> putUploadUrl(@RequestBody S3FileNameDto request) {
		String presignedUrl = this.s3Provider.generateUploadUrl(request.fileName());

		Map<String, String> result = Map.of("url", presignedUrl);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/download-url")
	public ResponseEntity<Map<String, String>> getUploadUrl(@RequestBody S3FileNameDto request) {
		String presignedUrl = this.s3Provider.generateDownloadUrl(request.fileName());

		Map<String, String> result = Map.of("url", presignedUrl);

		return ResponseEntity.ok(result);
	}
}
