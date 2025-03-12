package com.example.itauchallenge.dto;

import java.math.BigDecimal;

public record ClientResponseDTO(
        Long id,
        String firstName,
        String lastName,
        BigDecimal participation) {
}
