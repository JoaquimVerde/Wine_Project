package backend.Wine_Project.dto.orderDto;

public record OrderGetDto(
        Long clientId,
        double totalPrice
) {
}
