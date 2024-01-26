package backend.Wine_Project.dto.itemDto;

public record ItemGetDto(
        Long wineId,
        int quantity,
        double totalPrice
) {
}
