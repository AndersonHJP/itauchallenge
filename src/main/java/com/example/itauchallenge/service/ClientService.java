package com.example.itauchallenge.service;

import com.example.itauchallenge.dto.ClientDTO;
import com.example.itauchallenge.entity.Client;
import com.example.itauchallenge.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (client == null
                || client.getFirstName() == null
                || client.getLastName() == null
                || client.getParticipation() == null){
            throw new NullPointerException();
        }
        return clientRepository.save(client);

    }

    public List<Client> listAllClients() {
        List<Client> allClients = clientRepository.findAll();
        if (allClients == null){
            throw new NullPointerException();
        }
        return allClients;
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public Client update(Long id, ClientDTO clientDTO){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()){
            Client client = optionalClient.get();

            client.setFirstName(clientDTO.firstName());
            client.setLastName(clientDTO.lastName());
            client.setParticipation(clientDTO.participation());

            return clientRepository.save(client);
        } else {
            throw new RuntimeException("Client not found with ID: " + id);
        }
    }
}
