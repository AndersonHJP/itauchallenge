package com.example.itauchallenge.controller;

import com.example.itauchallenge.dto.ClientDTO;
import com.example.itauchallenge.entity.Client;
import com.example.itauchallenge.exception.ClienteNotFoundException;
import com.example.itauchallenge.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Tag(name = "Itau", description = "APIs for integration Itau")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/save")
    @Operation(summary = "Crie um novo cliente", description = "Crie um novo cliente com os detalhes fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Client> saveClient(
            @Parameter(description = "Dados do cliente a serem salvos", required = true)
            @RequestBody @Valid ClientDTO clientDTO) {
        Client savedClient = clientService.saveClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping("/listAll")
    @Operation(summary = "Listar todos os clientes", description = "Encontrar uma lista de todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
    })
    public ResponseEntity<List<Client>> listAllClients() {
        List<Client> clients = clientService.listAllClients();
        return ResponseEntity.ok(clients);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Excluir cliente com ID", description = "Exclui um cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<String> deleteCliente(
            @Parameter(description = "ID do cliente a ser excluído", required = true)
            @PathVariable("id") Long id) {
        try {
            String result = clientService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (ClienteNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente com o ID " + id + " não encontrado.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza um cliente existente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Entrada fornecida é inválida")
    })
    public ResponseEntity<Client> updateClient(
            @Parameter(description = "ID do cliente a ser atualizado", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Dados do cliente atualizados", required = true)
            @RequestBody ClientDTO clientDTO) {
        Client updatedClient = clientService.update(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }
}