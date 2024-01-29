package backend.Wine_Project.converter;

import backend.Wine_Project.converter.wineConverters.WineConverter;
import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.model.Item;

import java.util.Set;
import java.util.stream.Collectors;

public class ItemConverter {

    public static ItemCreateDto fromModelToItemCreateDto(Item item) {
        return new ItemCreateDto(
                item.getWine().getId(),
                item.getQuantity());
    }

    public static ItemGetDto fromModelToItemGetDto(Item item) {
        return new ItemGetDto(
                WineConverter.fromWineToWineReadRatingDto(item.getWine()),
                item.getQuantity(),
                item.getTotalPrice()
        );
    }

    public static Set<ItemGetDto> fromModelListToItemGetDtoList(Set<Item> items){
        return items.stream().map(ItemConverter::fromModelToItemGetDto).collect(Collectors.toSet());
    }

}
