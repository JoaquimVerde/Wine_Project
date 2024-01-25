package backend.Wine_Project.service;

import backend.Wine_Project.dtoItem.ItemCreateDto;
import backend.Wine_Project.dtoItem.ItemGetDto;

public interface ItemService extends CrudService<ItemGetDto, ItemCreateDto, Long>{

}
