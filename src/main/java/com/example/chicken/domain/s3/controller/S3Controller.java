package com.example.chicken.domain.s3.controller;

import static com.example.chicken.common.constant.PathConstant.S3.S3_PREFIX;
import static com.example.chicken.common.constant.PathConstant.S3.UPLOAD_URL;

import com.example.chicken.domain.s3.dto.UploadUrlsRequestDto;
import com.example.chicken.domain.s3.service.S3Provider;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(S3_PREFIX)
public class S3Controller {

    private final S3Provider s3Provider;

    @PostMapping(UPLOAD_URL)
    public ResponseEntity<List<String>> putUploadUrls(@RequestBody @Valid UploadUrlsRequestDto uploadUrls) {

        List<String> result = this.s3Provider.generateUploadUrls(uploadUrls);

        return ResponseEntity.ok(result);
    }

}
