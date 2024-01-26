package backend.Wine_Project.controller;

import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shoppingCarts")
public class ShoppingCartController {

    private final ShoppingCartServiceImp shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartServiceImp shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ShoppingCartGetDto>> getShoppingCarts() {
        return new ResponseEntity<>(shoppingCartService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> createShoppingCart(@RequestBody ShoppingCartCreateDto shoppingCart) {
        return new ResponseEntity<>(shoppingCartService.create(shoppingCart), HttpStatus.CREATED);
    }

}
