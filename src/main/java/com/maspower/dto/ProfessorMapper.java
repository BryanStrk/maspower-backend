package com.maspower.dto;

import com.maspower.model.Professor;

public class ProfessorMapper {

    public static ProfessorResponseDTO toDTO(Professor professor) {
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getName(),
                professor.getDni(),
                professor.getHiringYear(),
                professor.isActive(),
                professor.getImageUrl()
        );
    }

    public static Professor toEntity(ProfessorRequestDTO dto) {
        Professor professor = new Professor();
        professor.setName(dto.name());
        professor.setDni(dto.dni());
        professor.setHiringYear(dto.hiringYear());
        professor.setActive(dto.isActive());
        professor.setImageUrl(dto.imageUrl());
        return professor;
    }

    public static void updateEntity(Professor existing, ProfessorRequestDTO dto) {
        existing.setName(dto.name());
        existing.setDni(dto.dni());
        existing.setHiringYear(dto.hiringYear());
        existing.setActive(dto.isActive());
        existing.setImageUrl(dto.imageUrl());
    }
}