package backend.Wine_Project.controller;

import backend.Wine_Project.clientDto.ClientCreateDto;
import backend.Wine_Project.clientDto.ClientReadDto;
import backend.Wine_Project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientReadDto>> getClients() {
        return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody ClientCreateDto client) {
        return new ResponseEntity<>(clientService.create(client), HttpStatus.CREATED);
    }




}
