package service;

import com.example.itauchallenge.dto.ClientDTO;
import com.example.itauchallenge.entity.Client;
import com.example.itauchallenge.repository.ClientRepository;
import com.example.itauchallenge.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;
    private ClientDTO clientDTO;

    @BeforeEach
    public void setUp() {
        client = new Client(1L, "John", "Doe", 50.0);
        clientDTO = new ClientDTO("Jane", "Doe", 75.0);
    }

    @Test
    public void saveClient_ShouldSaveClient_WhenClientDTOIsValid() {
        when(clientRepository.save(ArgumentMatchers.any(Client.class))).thenReturn(client);

        Client savedClient = clientService.saveClient(clientDTO);

        Assertions.assertNotNull(savedClient);
        Assertions.assertEquals(client.getId(), savedClient.getId());
        verify(clientRepository, times(1)).save(ArgumentMatchers.any(Client.class));
    }

    @Test
    public void saveClient_ShouldThrowNullPointerException_WhenClientDTOIsInvalid() {
        ClientDTO invalidClientDTO = new ClientDTO(null, null, null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            clientService.saveClient(invalidClientDTO);
        });
    }

    @Test
    public void listAllClients_ShouldReturnAllClients_WhenClientsExist() {
        List<Client> clients = new ArrayList<>();
        clients.add(client);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.listAllClients();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void listAllClients_ShouldThrowNullPointerException_WhenNoClientsExist() {
        when(clientRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NullPointerException.class, () -> {
            clientService.listAllClients();
        });
    }

    @Test
    public void delete_ShouldDeleteClient_WhenClientExists() {
        doNothing().when(clientRepository).deleteById(client.getId());

        clientService.delete(client.getId());

        verify(clientRepository, times(1)).deleteById(client.getId());
    }

    @Test
    public void update_ShouldUpdateClient_WhenClientExists() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(ArgumentMatchers.any(Client.class))).thenReturn(client);

        Client updatedClient = clientService.update(client.getId(), clientDTO);

        Assertions.assertNotNull(updatedClient);
        Assertions.assertEquals(clientDTO.firstName(), updatedClient.getFirstName());
        Assertions.assertEquals(clientDTO.lastName(), updatedClient.getLastName());
        Assertions.assertEquals(clientDTO.participation(), updatedClient.getParticipation());
        verify(clientRepository, times(1)).findById(client.getId());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void update_ShouldThrowRuntimeException_WhenClientDoesNotExist() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            clientService.update(client.getId(), clientDTO);
        });
    }
}
