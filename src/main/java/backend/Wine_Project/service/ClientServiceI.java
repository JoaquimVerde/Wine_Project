package backend.Wine_Project.service;

import backend.Wine_Project.clientDto.ClientCreateDto;
import backend.Wine_Project.clientDto.ClientReadDto;

import java.util.List;

public interface ClientServiceI {

    List<ClientReadDto> getClients();

    ClientCreateDto addNewClient(ClientCreateDto client);
}
