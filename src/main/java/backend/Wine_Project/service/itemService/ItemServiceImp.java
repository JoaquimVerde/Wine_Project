package backend.Wine_Project.service.itemService;

import backend.Wine_Project.converter.ItemConverter;
import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.exceptions.ItemAlreadyExistsException;
import backend.Wine_Project.exceptions.ItemIdNotFoundException;
import backend.Wine_Project.exceptions.WineIdNotFoundException;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.repository.ItemRepository;
import backend.Wine_Project.service.wineService.WineService;
import backend.Wine_Project.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImp implements ItemService{

    private final ItemRepository itemRepository;
    private final WineService wineService;

    @Autowired
    public ItemServiceImp(ItemRepository itemRepository, WineService wineService) {
        this.itemRepository = itemRepository;
        this.wineService = wineService;
    }

    @Override
    public List<ItemGetDto> getAll() {
        List<Item> items = this.itemRepository.findAll();
        return items.stream()
                .map(ItemConverter::fromModelToItemGetDto)
                .toList();
    }



    @Override
    public Long create(ItemCreateDto item) {
        Wine wine = wineService.getById(item.wineId());
        Optional<Item> itemOptional = this.itemRepository.findById(item.wineId());
        if (itemOptional.isPresent())
            throw new ItemAlreadyExistsException(Messages.ITEM_ALREADY_EXISTS.getMessage());
        Item itemToAdd = new Item(wine, item.quantity());
        itemRepository.save(itemToAdd);
        return itemToAdd.getId();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, ItemGetDto modelUpdateDto) {

    }

    @Override
    public ItemGetDto get(Long id) {
        return null;
    }

    public Item getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new WineIdNotFoundException(Messages.ITEM_ID_NOT_FOUND.getMessage() + id);
        }
        return optionalItem.get();
    }
    @Override
    public Item getById(Long id){
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ItemIdNotFoundException(Messages.ITEM_ID_NOT_FOUND.getMessage() + id);
        }
        return optionalItem.get();
    }
}
