package backend.Wine_Project.converter;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.model.Item;

public class ItemConverter {

    public static ItemCreateDto fromModelToItemCreateDto(Item item) {
        return new ItemCreateDto(
                item.getWine().getId(),
                item.getQuantity());
    }

    public static ItemGetDto fromModelToItemGetDto(Item item) {
        return new ItemGetDto(
                item.getWine().getId(),
                item.getQuantity(),
                item.getTotalPrice()
        );
    }

}
