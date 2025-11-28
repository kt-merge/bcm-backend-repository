package com.example.chicken.dto;

import jakarta.validation.constraints.NotBlank;

public record S3FileNameDto(@NotBlank String fileName) {
}
