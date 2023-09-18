package com.XAUS.DTOS;

import com.XAUS.Models.User;

public record LoginResponseDTO(Long id, String email, String name, String message) {

    public LoginResponseDTO(User user, String message) {
        this(user.getId(), user.getEmail(), user.getName(), message);
    }

    public static LoginResponseDTO createUnauthorizedResponse() {
        return new LoginResponseDTO(null, null, null, "Credenciais inválidas");
    }
}
