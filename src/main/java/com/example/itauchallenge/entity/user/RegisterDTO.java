package com.example.itauchallenge.entity.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegisterDTO", description = "Dados para registro de novo usuário")
public record RegisterDTO(
        @Schema(description = "Email ou nome de usuário", example = "novo.usuario@example.com", required = true)
        String login,

        @Schema(description = "Senha do usuário", example = "senhaSegura123", required = true, minLength = 6)
        String password,

        @Schema(description = "Tipo de usuário", example = "USER", allowableValues = {"USER", "ADMIN"}, required = true)
        UserRole role
) {}
