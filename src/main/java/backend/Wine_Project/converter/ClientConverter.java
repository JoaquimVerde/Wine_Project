package backend.Wine_Project.converter;

import backend.Wine_Project.dtoClient.ClientCreateDto;
import backend.Wine_Project.dtoClient.ClientReadDto;
import backend.Wine_Project.dtoClient.ClientReadRatingDto;
import backend.Wine_Project.model.Client;

import java.util.List;

public class ClientConverter {

    public static ClientReadDto fromModelToClientReadDto (Client client){
        return new ClientReadDto(
                client.getName(),
                client.getEmail()
        );
    }

    public static List<ClientReadDto> fromModelListToClientReadDtoList(List<Client> clients){
        return clients.stream().map(ClientConverter::fromModelToClientReadDto).toList();
    }

    public static Client fromClientCreateDtoToModel (ClientCreateDto client){
        return new Client(
                client.name(),
                client.email(),
                client.nif()
        );
    }

    public static ClientReadRatingDto fromModelToClientReadRatingDto (Client client){
        return new ClientReadRatingDto(
                client.getName()
        );
    }
}
