package com.maspower.dto;

public record ProfessorResponseDTO(
        Long id,
        String name,
        String dni,
        int hiringYear,
        boolean isActive,
        String imageUrl
) {}