package backend.Wine_Project.dto.shoppingCartDto;


import java.util.Set;

public record ShoppingCartUpdateDto(
        Set<Long> itemsId
) {

}
