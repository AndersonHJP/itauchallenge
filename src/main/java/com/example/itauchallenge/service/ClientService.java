package com.example.itauchallenge.service;

import com.example.itauchallenge.dto.ClientDTO;
import com.example.itauchallenge.entity.Client;
import com.example.itauchallenge.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {


    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client saveClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setFirstName(clientDTO.firstName());
        client.setLastName(clientDTO.lastName());
        client.setParticipation(clientDTO.participation());
        return clientRepository.save(client);

    }

    public List<Client> listAllClients() {
        return clientRepository.findAll();
    }
}
