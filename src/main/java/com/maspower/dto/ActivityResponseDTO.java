package com.maspower.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record ActivityResponseDTO(
        Long id,
        String title,
        String description,
        double price,
        LocalDateTime date,
        String imageUrl,
        ProfessorSummaryDTO professor,
        Set<UserSummaryDTO> users
) {}