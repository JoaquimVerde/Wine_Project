package backend.Wine_Project.controller;

import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartUpdateDto;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartService;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shoppingCarts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartServiceImp shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    @Operation(summary = "Get all shopping carts", description = "Returns all shopping carts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all shopping carts")
    @GetMapping("/")
    public ResponseEntity<List<ShoppingCartGetDto>> getShoppingCarts() {
        return new ResponseEntity<>(shoppingCartService.getAll(), HttpStatus.OK);
    }
    @Operation(summary = "Create new shopping cart", description = "Create a new shopping cart with given parameters")
    @ApiResponse(responseCode = "201", description = "Successfully created")
    @Parameter(name = "shoppingCart", description = "ShoppingCartCreateDto object to be created", example = "clientId: 1, items: [{itemId: 1, quantity: 2}, {itemId: 2, quantity: 3}]")
    @PostMapping("/")
    public ResponseEntity<Long> createShoppingCart(@RequestBody ShoppingCartCreateDto shoppingCart) {
        return new ResponseEntity<>(shoppingCartService.create(shoppingCart), HttpStatus.CREATED);
    }
    @Operation(summary = "Delete shopping cart", description = "Delete a shopping cart with given id")
    @ApiResponse(responseCode = "200", description = "Successfully deleted")
    @Parameter(name = "shoppingCartID", description = "Shopping cart id to delete", example = "1")
    @DeleteMapping(path = "{shoppingCartID}")
    public ResponseEntity<ShoppingCart> deleteShoppingCart(@PathVariable("shoppingCartID") Long shoppingCartID ) {
        shoppingCartService.delete(shoppingCartID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Update shopping cart", description = "Update a shopping cart with given parameters")
    @ApiResponse(responseCode = "200", description = "Successfully updated")
    @Parameter(name = "shoppingCartId", description = "Shopping cart id to update", example = "1")
    @Parameter(name = "shoppingCartUpdateDto", description = "ShoppingCartUpdateDto object to be updated", example = "clientId: 1, items: [{itemId: 1, quantity: 2}, {itemId: 2, quantity: 3}]")
    @PatchMapping(path = "{shoppingCartId}")
    public ResponseEntity<Long> updateShoppingCart(@PathVariable("shoppingCartId") Long shoppingCartId, @RequestBody ShoppingCartUpdateDto shoppingCartUpdateDto) {
        shoppingCartService.update(shoppingCartId, shoppingCartUpdateDto);
        return new ResponseEntity<>(shoppingCartId,HttpStatus.OK);
    }

}
