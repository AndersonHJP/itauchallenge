package com.example.itauchallenge.entity.client;

import java.math.BigDecimal;

public record ClientResponseDTO(
        Long id,
        String firstName,
        String lastName,
        BigDecimal participation) {
}
