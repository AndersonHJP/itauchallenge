package service;

import com.example.itauchallenge.dto.ClientDTO;
import com.example.itauchallenge.dto.ClientResponseDTO;
import com.example.itauchallenge.entity.Client;
import com.example.itauchallenge.exception.ClienteNotFoundException;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        client = new Client(1L, "John", "Doe", new BigDecimal(20.0));
        clientDTO = new ClientDTO("Jane", "Doe", new BigDecimal(500));
    }

    @Test
    public void saveClient_ShouldSaveClient_WhenClientDTOIsValid() {
        when(clientRepository.save(ArgumentMatchers.any(Client.class))).thenReturn(client);

        ClientResponseDTO savedClient = clientService.saveClient(clientDTO);

        Assertions.assertNotNull(savedClient);
        assertEquals(client.getId(), savedClient.id());
        assertEquals(client.getFirstName(), savedClient.firstName());
        assertEquals(client.getLastName(), savedClient.lastName());
        assertEquals(client.getParticipation(), savedClient.participation());

        verify(clientRepository, times(1)).save(ArgumentMatchers.any(Client.class));
    }

    @Test
    public void saveClient_ShouldThrowIllegalArgumentException_WhenClientDTOIsInvalid() {
        ClientDTO invalidClientDTO = new ClientDTO(null, null, null);

        assertThrows(IllegalArgumentException.class, () -> {
            clientService.saveClient(invalidClientDTO);
        });
    }

    @Test
    public void listAllClients_ShouldReturnAllClients_WhenClientsExist() {
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        when(clientRepository.findAll()).thenReturn(clients);
        List<ClientResponseDTO> result = clientService.listAllClients();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void listAllClients_ShouldReturnEmpty_WhenNoClientsExist() {
        when(clientRepository.findAll()).thenReturn(new ArrayList<>());
        List<ClientResponseDTO> result = clientService.listAllClients();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void delete_ShouldDeleteClient_WhenClientExists() {
        when(clientRepository.existsById(client.getId())).thenReturn(true);  // Mock para verificar a existência do cliente
        doNothing().when(clientRepository).deleteById(client.getId());  // Mock para a exclusão do cliente
        String result = clientService.delete(client.getId());
        assertEquals("Usuário deletado com sucesso", result);  // Verifica se a resposta é a mensagem de sucesso

        verify(clientRepository, times(1)).deleteById(client.getId());
    }

    @Test
    public void delete_ShouldThrowException_WhenClientDoesNotExist() {
        when(clientRepository.existsById(client.getId())).thenReturn(false);  // Mock para verificar a inexistência do cliente

        // Act & Assert: Chama o método de serviço e verifica se a exceção é lançada
        Exception exception = assertThrows(ClienteNotFoundException.class, () -> {
            clientService.delete(client.getId());
        });
        assertEquals("Cliente não encontrado com o ID: " + client.getId(), exception.getMessage());
        verify(clientRepository, times(0)).deleteById(client.getId());
    }

    @Test
    public void update_ShouldUpdateClient_WhenClientExists() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(ArgumentMatchers.any(Client.class))).thenReturn(client);

        ClientResponseDTO updatedClient = clientService.update(client.getId(), clientDTO);

        Assertions.assertNotNull(updatedClient);
        assertEquals(clientDTO.firstName(), updatedClient.firstName());
        assertEquals(clientDTO.lastName(), updatedClient.lastName());
        assertEquals(clientDTO.participation(), updatedClient.participation());

        verify(clientRepository, times(1)).findById(client.getId());
        verify(clientRepository, times(1)).save(ArgumentMatchers.any(Client.class));
    }

    @Test
    public void update_ShouldThrowClienteNotFoundException_WhenClientDoesNotExist() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> {
            clientService.update(client.getId(), clientDTO);
        });
    }
}
