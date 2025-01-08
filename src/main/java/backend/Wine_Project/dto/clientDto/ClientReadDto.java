package backend.Wine_Project.dto.clientDto;

import backend.Wine_Project.dto.wineDto.WineReadDto;

import java.util.Set;

public record ClientReadDto(

        String name,
        String email,
        Set<WineReadDto> ratedWines

) {
}
