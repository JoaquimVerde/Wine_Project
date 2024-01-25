package backend.Wine_Project.dtoItem;

public record ItemGetDto(
        Long wineId,
        int quantity,
        double itemTotalAmount
) {
}
