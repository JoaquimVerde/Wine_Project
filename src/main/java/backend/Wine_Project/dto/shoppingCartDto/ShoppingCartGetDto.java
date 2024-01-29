package backend.Wine_Project.dto.shoppingCartDto;

import backend.Wine_Project.dto.clientDto.ClientReadDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;

import java.util.Set;

public record ShoppingCartGetDto(
        Long id,
        ClientReadDto client,
        Set<ItemGetDto> itemsSet,
        double totalAmount


) {
}
