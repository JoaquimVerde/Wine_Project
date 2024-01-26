package backend.Wine_Project.controller;

import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.service.itemService.ItemServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemServiceImp itemService;

    public ItemController(ItemServiceImp itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ItemGetDto>> getItems() {
        return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
    }
}
