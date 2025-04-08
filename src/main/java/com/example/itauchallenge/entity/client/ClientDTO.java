package com.example.itauchallenge.entity.client;


import java.math.BigDecimal;

public record ClientDTO(
        String firstName,

        String lastName,

        BigDecimal participation
) {}
