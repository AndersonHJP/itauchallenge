package repository;

import com.example.itauchallenge.ItauchallengeApplication;
import com.example.itauchallenge.entity.Client;
import com.example.itauchallenge.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = ItauchallengeApplication.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client("John", "Doe", new BigDecimal(500));
    }

    @Test
    public void saveClient_ShouldReturnSavedClient() {
        Client savedClient = clientRepository.save(client);
        Assertions.assertNotNull(savedClient);
        Assertions.assertEquals(client.getFirstName(), savedClient.getFirstName());
        Assertions.assertEquals(client.getLastName(), savedClient.getLastName());
        Assertions.assertEquals(client.getParticipation(), savedClient.getParticipation());
    }

    @Test
    public void findClientById_ShouldReturnClient_WhenClientExists() {
        Client savedClient = clientRepository.save(client);
        Optional<Client> foundClient = clientRepository.findById(savedClient.getId());
        Assertions.assertTrue(foundClient.isPresent());
        Assertions.assertEquals(savedClient.getId(), foundClient.get().getId());
    }

    @Test
    public void findAllClients_ShouldReturnAllClients() {
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();
        Assertions.assertNotNull(clients);
        Assertions.assertFalse(clients.isEmpty());
    }

    @Test
    public void deleteClient_ShouldRemoveClient() {
        Client savedClient = clientRepository.save(client);
        clientRepository.deleteById(savedClient.getId());
        Optional<Client> foundClient = clientRepository.findById(savedClient.getId());
        Assertions.assertFalse(foundClient.isPresent());
    }

    @Test
    public void updateClient_ShouldReturnUpdatedClient() {
        Client savedClient = clientRepository.save(client);
        savedClient.setFirstName("Jane");
        savedClient.setLastName("Smith");
        savedClient.setParticipation(new BigDecimal(750));
        Client updatedClient = clientRepository.save(savedClient);
        Assertions.assertEquals("Jane", updatedClient.getFirstName());
        Assertions.assertEquals("Smith", updatedClient.getLastName());
        Assertions.assertEquals(new BigDecimal(750), updatedClient.getParticipation());
    }
}
