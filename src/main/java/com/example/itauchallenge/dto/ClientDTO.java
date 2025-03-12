package com.example.itauchallenge.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ClientDTO(
        @NotBlank(message = "O primeiro nome não pode estar em branco")
        String firstName,

        @NotBlank(message = "O sobrenome não pode estar em branco")
        String lastName,

        @NotNull(message = "A participação não pode ser nula")
        BigDecimal participation
) {}
