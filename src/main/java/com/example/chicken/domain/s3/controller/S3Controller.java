package com.example.chicken.domain.s3.controller;

import static com.example.chicken.common.constant.PathConstant.S3.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.domain.s3.service.S3Provider;
import com.example.chicken.dto.S3FileNameDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(S3_PREFIX)
public class S3Controller {

	private final S3Provider s3Provider;

	@PostMapping(UPLOAD_URL)
	public ResponseEntity<Map<String, String>> putUploadUrl(@RequestBody @Valid S3FileNameDto request) {
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
