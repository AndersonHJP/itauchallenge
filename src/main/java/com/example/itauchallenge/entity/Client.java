package com.example.itauchallenge.entity;

import com.example.itauchallenge.dto.ClientDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter // Lombok: gera os getters automaticamente
@Setter
@Builder // Lombok: permite criar objetos usando o padrão Builder
@NoArgsConstructor // Lombok: gera um construtor vazio (necessário para JPA)
@AllArgsConstructor // Lombok: gera um construtor com todos os atributos
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
}