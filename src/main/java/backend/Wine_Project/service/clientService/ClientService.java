package backend.Wine_Project.service.clientService;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.dto.clientDto.ClientReadDto;
import backend.Wine_Project.dto.clientDto.ClientUpdateDto;
import backend.Wine_Project.model.Client;

import java.util.List;

public interface ClientService {


    List<ClientReadDto> getAll();

    Long create(ClientCreateDto client);

    Long updateClient(Long id, ClientUpdateDto client);

    Client getById(Long id);

    List<ClientCreateDto> createCostumers(List<ClientCreateDto> clients);

    void saveClient(Client client);
}
