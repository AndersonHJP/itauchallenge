package com.example.itauchallenge.dto;


import java.math.BigDecimal;

public record ClientDTO(
        String firstName,

        String lastName,

        BigDecimal participation
) {}
