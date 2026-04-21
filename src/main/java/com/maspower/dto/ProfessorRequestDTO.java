package com.maspower.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfessorRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Dni is required")
    private String dni;

    @Min(value = 2000, message = "Hiring year must be 2000 or later")
    private int hiringYear;

    private boolean isActive;

    private String imageUrl;
}