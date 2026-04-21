package com.apexfitness.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record ActivityRequestDTO(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @Positive(message = "Price must be positive")
        double price,

        @NotNull(message = "Date is required")
        @Future(message = "Date must be in the future")
        LocalDateTime date,

        String imageUrl,

        @NotNull(message = "Professor id is required")
        Long professorId
) {}