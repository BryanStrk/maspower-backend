package com.apexfitness.dto;

public record UserSummaryDTO(
        Long id,
        String name,
        String surname,
        String dni,
        boolean isActive
) {}