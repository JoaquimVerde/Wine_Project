package backend.Wine_Project.orderDto;

public record OrderCreateDto(
        Long clientId,
        double totalPrice

) {
}
