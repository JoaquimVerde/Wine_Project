package backend.Wine_Project.service;

import backend.Wine_Project.dtoClient.ClientCreateDto;
import backend.Wine_Project.dtoClient.ClientReadDto;
import backend.Wine_Project.model.Client;

import java.util.List;

public interface ClientService {


    List<ClientReadDto> getAll();

    Long create(ClientCreateDto client);

    Client getById(Long id);
}
