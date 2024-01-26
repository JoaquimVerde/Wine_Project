package backend.Wine_Project.dto.shoppingCartDto;

import backend.Wine_Project.model.Item;

import java.util.Set;

public record ShoppingCartGetDto(
        Long id,
        Set<Item> itemsSet

) {
}
