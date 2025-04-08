package com.example.itauchallenge.controller;

import com.example.itauchallenge.entity.client.ClientDTO;
import com.example.itauchallenge.entity.client.ClientResponseDTO;
import com.example.itauchallenge.exception.ClienteNotFoundException;
import com.example.itauchallenge.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        ClientResponseDTO savedClientDTO = clientService.saveClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClientDTO);
    }

    @GetMapping("/listAll")
    @Operation(summary = "Listar todos os clientes", description = "Encontrar uma lista de todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
    })
    public ResponseEntity<List<ClientResponseDTO>> listAllClients() {
        List<ClientResponseDTO> responseDTOs = clientService.listAllClients(); // Use o retorno direto do service
        return ResponseEntity.ok(responseDTOs);
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
    public ResponseEntity<ClientResponseDTO> updateClient(
            @Parameter(description = "ID do cliente a ser atualizado", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Dados do cliente atualizado", required = true)
            @RequestBody ClientDTO clientDTO) {
        ClientResponseDTO responseDTO = clientService.update(id, clientDTO);
        return ResponseEntity.ok(responseDTO);
    }
}