package backend.Wine_Project.controller;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.dto.clientDto.ClientReadDto;
import backend.Wine_Project.service.clientService.ClientServiceImp;
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

    @GetMapping("/")
    public ResponseEntity<List<ClientReadDto>> getClients() {
        return new ResponseEntity<>(clientServiceImp.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> addNewClient(@RequestBody ClientCreateDto client) {
        return new ResponseEntity<>(clientServiceImp.create(client), HttpStatus.CREATED);
    }




}
