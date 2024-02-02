package backend.Wine_Project.service.itemService;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.model.Item;

import java.util.List;

public interface ItemService {

    List<ItemGetDto> getAll();

    Long create(ItemCreateDto item);

    Item getById(Long id);
}
