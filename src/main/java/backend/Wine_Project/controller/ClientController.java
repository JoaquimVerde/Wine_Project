package backend.Wine_Project.controller;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.dto.clientDto.ClientReadDto;
import backend.Wine_Project.dto.clientDto.ClientUpdateDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.service.clientService.ClientServiceImp;
import backend.Wine_Project.util.Messages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientServiceImp clientServiceImp;

    @Autowired
    public ClientController(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @Operation(summary = "Get all clients", description = "Returns all clients")
    @ApiResponse(responseCode = "200", description = "Successfully retrieve")
    @Parameter(name = "pageNumber", description = "Page number to retrieve", example = "1")
    @GetMapping("/")
    public ResponseEntity<List<ClientReadDto>> getClients() {
        return new ResponseEntity<>(clientServiceImp.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Create a new client", description = "Create a new client with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The client email or nif already existed")})
    @Parameter(name = "client", description = "ClientCreateDto object to be created", example = "name: John, email:john@example.com, nif: 123456789")
    @PostMapping("/")
    public ResponseEntity<String> addNewClient(@Valid @RequestBody ClientCreateDto client) {
        Long id = clientServiceImp.create(client);
        return new ResponseEntity<>(Messages.CLIENT_CREATED.getMessage()+" - id: "+id, HttpStatus.CREATED);
    }

    @Operation(summary = "Create a list of clients", description = "Create a list of clients with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One clients nif or email already existed")})
    @Parameter(name = "clients", description = "List of ClientCreateDto objects to be created", example = "[name: John, email:jonh@example.com, nif: 123456789, name: Mary, email:mary@example.com, nif: 987654321]")
    @PostMapping("/addClients")
    public ResponseEntity<List<ClientCreateDto>> addNewClients(@Valid @RequestBody List<ClientCreateDto> clients) {
        return new ResponseEntity<>(clientServiceImp.createCostumers(clients), HttpStatus.CREATED);
    }

    @Parameter(name = "client", description = "client id to be updated", example = "1")
    @PatchMapping(path = "{clientId}")
    public ResponseEntity<String> updateClient(@PathVariable("clientId") Long id, @Valid @RequestBody ClientUpdateDto client) {
        clientServiceImp.updateClient(id, client);
        return new ResponseEntity<>("Client id: "+id+" successfully updated", HttpStatus.OK);
    }


}
