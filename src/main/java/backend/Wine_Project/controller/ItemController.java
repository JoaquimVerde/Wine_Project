package backend.Wine_Project.controller;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.service.itemService.ItemServiceImp;
import backend.Wine_Project.util.Messages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemServiceImp itemService;

    @Autowired
    public ItemController(ItemServiceImp itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Get all items", description = "Returns all items")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all items")
    @GetMapping("/")
    public ResponseEntity<List<ItemGetDto>> getItems() {
        return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Create new order", description = "Create a new order with given parameters")
    @ApiResponse(responseCode = "201", description = "Successfully created")
    @Parameter(name = "item", description = "ItemCreateDto object to be created", example = "name: Wine, quantity: 2, price: 20.0")
    @PostMapping("/")
    public ResponseEntity<String> addNewOrder(@RequestBody ItemCreateDto item) {
        Long id = itemService.create(item);
        return new ResponseEntity<>(Messages.ITEM_CREATED.getMessage() + " - id: "+ id, HttpStatus.CREATED);
    }


}
