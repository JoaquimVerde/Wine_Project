package backend.Wine_Project.controller;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.dto.clientDto.ClientReadDto;
import backend.Wine_Project.service.clientService.ClientServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/")
    public ResponseEntity<List<ClientReadDto>> getClients() {
        return new ResponseEntity<>(clientServiceImp.getAll(), HttpStatus.OK);
    }
    @Operation(summary = "Create a new client", description = "Create a new client with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The client email or nif already existed")})
    @PostMapping("/")
    public ResponseEntity<Long> addNewClient(@Valid @RequestBody ClientCreateDto client) {

        return new ResponseEntity<>(clientServiceImp.create(client), HttpStatus.CREATED);
    }
    @Operation(summary = "Create a list of clients", description = "Create a list of clients with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One clients nif or email already existed")})
    @PostMapping("/addClients")
    public ResponseEntity<List<ClientCreateDto>> addNewClients(@Valid@RequestBody List<ClientCreateDto> clients) {
        return new ResponseEntity<>(clientServiceImp.createCostumers(clients), HttpStatus.CREATED);
    }







}
