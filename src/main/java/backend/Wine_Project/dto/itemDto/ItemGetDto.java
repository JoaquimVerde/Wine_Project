package backend.Wine_Project.dto.itemDto;

import backend.Wine_Project.dto.wineDto.WineReadRatingDto;

public record ItemGetDto(
        WineReadRatingDto wine,
        int quantity,
        double totalPrice
) {
}
