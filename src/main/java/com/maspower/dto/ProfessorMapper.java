package com.maspower.dto;

import com.maspower.model.Professor;

public class ProfessorMapper {

    public static ProfessorResponseDTO toDTO(Professor professor) {
        ProfessorResponseDTO dto = new ProfessorResponseDTO();
        dto.setId(professor.getId());
        dto.setName(professor.getName());
        dto.setDni(professor.getDni());
        dto.setHiringYear(professor.getHiringYear());
        dto.setActive(professor.isActive());
        dto.setImageUrl(professor.getImageUrl());
        return dto;
    }

    public static Professor toEntity(ProfessorRequestDTO dto) {
        Professor professor = new Professor();
        professor.setName(dto.getName());
        professor.setDni(dto.getDni());
        professor.setHiringYear(dto.getHiringYear());
        professor.setActive(dto.isActive());
        professor.setImageUrl(dto.getImageUrl());
        return professor;
    }

    public static void updateEntity(Professor existing, ProfessorRequestDTO dto) {
        existing.setName(dto.getName());
        existing.setDni(dto.getDni());
        existing.setHiringYear(dto.getHiringYear());
        existing.setActive(dto.isActive());
        existing.setImageUrl(dto.getImageUrl());
    }
}