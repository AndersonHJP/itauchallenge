package com.example.itauchallenge.entity.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthenticationDTO", description = "Credenciais para login")
public record AuthenticationDTO(
        @Schema(description = "Email ou nome de usuário", example = "admin@example.com", required = true)
        String login,

        @Schema(description = "Senha do usuário", example = "senha123", required = true, minLength = 6)
        String password
) {}
