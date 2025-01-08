package backend.Wine_Project.service.itemService;

import backend.Wine_Project.converter.ItemConverter;
import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.exceptions.alreadyExists.ItemAlreadyExistsException;
import backend.Wine_Project.exceptions.notFound.ItemIdNotFoundException;
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
public class ItemServiceImp implements ItemService {

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
        if (item.wineId() < 1) {
            throw new IllegalArgumentException(Messages.WINE_ID_MUST_NOT_BE_LESS_THAN_1.getMessage());
        }
        Wine wine = wineService.getById(item.wineId());
        Optional<Item> itemOptional = this.itemRepository.findByWineAndQuantity(wine, item.quantity());
        if (itemOptional.isPresent())
            throw new ItemAlreadyExistsException(Messages.ITEM_ALREADY_EXISTS.getMessage() + itemOptional.get().getId());
        Item itemToAdd = new Item(wine, item.quantity());
        itemRepository.save(itemToAdd);
        wine.setItem(true);
        wineService.saveWine(wine);
        return itemToAdd.getId();
    }


    @Override
    public Item getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ItemIdNotFoundException(Messages.ITEM_ID_NOT_FOUND.getMessage() + id);
        }
        return optionalItem.get();
    }


}
