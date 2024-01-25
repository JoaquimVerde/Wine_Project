package backend.Wine_Project.dto.orderDto;

public record OrderCreateDto(
        Long clientId,
        double totalPrice

) {
}
