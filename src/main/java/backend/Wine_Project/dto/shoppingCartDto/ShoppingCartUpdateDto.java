package backend.Wine_Project.dto.shoppingCartDto;


import jakarta.validation.constraints.Size;

import java.util.Set;

public record ShoppingCartUpdateDto(
        Set<Long> itemsId
) {

}
