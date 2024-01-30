package backend.Wine_Project.controller;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.dto.clientDto.ClientReadDto;
import backend.Wine_Project.service.clientService.ClientServiceImp;
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

    @GetMapping("/")
    public ResponseEntity<List<ClientReadDto>> getClients() {
        return new ResponseEntity<>(clientServiceImp.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> addNewClient(@Valid @RequestBody ClientCreateDto client) {

        return new ResponseEntity<>(clientServiceImp.create(client).toString(), HttpStatus.CREATED);
    }





}
