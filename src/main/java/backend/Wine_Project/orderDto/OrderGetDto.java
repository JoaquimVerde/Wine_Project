package backend.Wine_Project.orderDto;

public record OrderGetDto(
        Long clientId,
        double totalPrice
) {
}
