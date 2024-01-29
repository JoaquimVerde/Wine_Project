package backend.Wine_Project.service.itemService;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.service.CrudService;

public interface ItemService extends CrudService<ItemGetDto, ItemCreateDto, Long> {

    Item getById(Long id);
}
