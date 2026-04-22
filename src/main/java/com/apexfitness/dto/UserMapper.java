package com.apexfitness.dto;

import com.apexfitness.model.Role;
import com.apexfitness.model.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getDni(),
                user.getRegistrationYear(),
                user.isActive(),
                user.getImageUrl(),
                user.getRole()
        );
    }

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setDni(dto.dni());
        user.setRegistrationYear(dto.registrationYear());
        user.setActive(dto.isActive());
        user.setImageUrl(dto.imageUrl());
        user.setRole(dto.role() != null ? dto.role() : Role.CLIENT);
        return user;
    }

    public static void updateEntity(User existing, UserRequestDTO dto) {
        existing.setName(dto.name());
        existing.setSurname(dto.surname());
        existing.setDni(dto.dni());
        existing.setRegistrationYear(dto.registrationYear());
        existing.setActive(dto.isActive());
        existing.setImageUrl(dto.imageUrl());
        if (dto.role() != null) {
            existing.setRole(dto.role());
        }
    }
}