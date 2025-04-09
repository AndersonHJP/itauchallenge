package com.example.itauchallenge.service;

import com.example.itauchallenge.entity.client.ClientDTO;
import com.example.itauchallenge.entity.client.ClientResponseDTO;
import com.example.itauchallenge.entity.client.Client;
import com.example.itauchallenge.exception.ClienteNotFoundException;
import com.example.itauchallenge.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDTO saveClient(ClientDTO clientDTO) {
        if (clientDTO == null || clientDTO.firstName() == null || clientDTO.firstName().isEmpty() ||
                clientDTO.lastName() == null || clientDTO.lastName().isEmpty() || clientDTO.participation() == null) {
            throw new IllegalArgumentException("Campos obrigatórios não podem ser nulos");
        }

        Client client = new Client(clientDTO);
        Client savedClient = clientRepository.save(client);
        return new ClientResponseDTO(savedClient.getId(), savedClient.getFirstName(), savedClient.getLastName(), savedClient.getParticipation());
    }

    public List<ClientResponseDTO> listAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientResponseDTO(client.getId(), client.getFirstName(), client.getLastName(), client.getParticipation()))
                .collect(Collectors.toList());
    }

    public String delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return "Usuário deletado com sucesso";
        } else {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com o ID: " + id));

        client.setFirstName(clientDTO.firstName());
        client.setLastName(clientDTO.lastName());
        client.setParticipation(clientDTO.participation());

        Client updatedClient = clientRepository.save(client);
        return new ClientResponseDTO(updatedClient.getId(), updatedClient.getFirstName(), updatedClient.getLastName(), updatedClient.getParticipation());
    }
}
