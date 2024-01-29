package backend.Wine_Project.dto.shoppingCartDto;

import java.util.Set;

public record ShoppingCartCreateDto(

        Long clientId,
        Set<Long> itemsId

) {
}
