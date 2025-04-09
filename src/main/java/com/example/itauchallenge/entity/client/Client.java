package com.example.itauchallenge.entity.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O primeiro nome não pode estar em branco ou vazio")
    private String firstName;

    @NotBlank(message = "O sobrenome não pode estar em branco ou vazio")
    private String lastName;

    @NotNull(message = "A participação não pode ser nula")
    @PositiveOrZero(message = "A participação deve ser um valor positivo ou zero")
    private BigDecimal participation;

    // Construtor que recebe um ClientDTO
    public Client(ClientDTO clientDTO) {
        this.firstName = clientDTO.firstName();
        this.lastName = clientDTO.lastName();
        this.participation = clientDTO.participation();
    }
    public Client(String firstName, String lastName, BigDecimal participation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.participation = participation;
    }
}