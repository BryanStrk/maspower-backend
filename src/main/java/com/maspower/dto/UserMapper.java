package com.maspower.dto;

import com.maspower.model.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setDni(user.getDni());
        dto.setRegistrationYear(user.getRegistrationYear());
        dto.setActive(user.isActive());
        dto.setImageUrl(user.getImageUrl());
        return dto;
    }

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setDni(dto.getDni());
        user.setRegistrationYear(dto.getRegistrationYear());
        user.setActive(dto.isActive());
        user.setImageUrl(dto.getImageUrl());
        return user;
    }

    public static void updateEntity(User existing, UserRequestDTO dto) {
        existing.setName(dto.getName());
        existing.setSurname(dto.getSurname());
        existing.setDni(dto.getDni());
        existing.setRegistrationYear(dto.getRegistrationYear());
        existing.setActive(dto.isActive());
        existing.setImageUrl(dto.getImageUrl());
    }
}