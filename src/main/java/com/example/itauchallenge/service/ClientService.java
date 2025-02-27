package com.example.itauchallenge.service;

import com.example.itauchallenge.dto.ClientDTO;
import com.example.itauchallenge.entity.Client;
import com.example.itauchallenge.exception.ClienteNotFoundException;
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
        return clientRepository.save(client);
    }

    public List<Client> listAllClients() {
        return clientRepository.findAll();
    }

    public String delete(Long id) {
        Optional<Client> deleteClient = clientRepository.findById(id);

        if (deleteClient.isPresent()) {
            clientRepository.deleteById(id);
            return "Usuário deletado com sucesso";
        } else {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }
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
            throw new RuntimeException("Cliente não encontrando com o ID: " + id);
        }
    }
}
