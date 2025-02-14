package com.example.itauchallenge.entity;

import com.example.itauchallenge.dto.ClientDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
@Builder
@Entity
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String firstName;

    @NotBlank(message = "O sobrenome é obrigatório")
    private String lastName;

    @NotBlank(message = "A participação é obrigatória")
    private Double participation;

    public Client(ClientDTO clientDTO) {
    }

    public Client() {

    }

    public @NotBlank(message = "O nome é obrigatório") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "O nome é obrigatório") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "O sobrenome é obrigatório") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "O sobrenome é obrigatório") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "A participação é obrigatória") Double getParticipation() {
        return participation;
    }

    public void setParticipation(@NotBlank(message = "A participação é obrigatória") Double participation) {
        this.participation = participation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
