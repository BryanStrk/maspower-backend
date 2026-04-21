package com.maspower.dto;

import lombok.Data;

@Data
public class ProfessorResponseDTO {
    private Long id;
    private String name;
    private String dni;
    private int hiringYear;
    private boolean isActive;
    private String imageUrl;
}