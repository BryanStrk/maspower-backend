package com.maspower.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProfessorRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Dni is required")
        String dni,

        @Min(value = 2000, message = "Hiring year must be 2000 or later")
        int hiringYear,

        boolean isActive,

        String imageUrl
) {}