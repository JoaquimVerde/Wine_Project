package backend.Wine_Project.controller;

import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.dto.orderDto.OrderGetDto;
import backend.Wine_Project.dto.orderDto.OrderUpdateDto;
import backend.Wine_Project.service.orderService.OrderServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/wine_orders")
public class OrderController {

    private final OrderServiceImp orderService;

    @Autowired
    public OrderController(OrderServiceImp orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get all orders", description = "Returns all orders")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all orders")
    @GetMapping("/")
    public ResponseEntity<List<OrderGetDto>> getOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Create new order", description = "Create a new order with given parameters")
    @ApiResponse(responseCode = "201", description = "Successfully created")
    @Parameter(name = "order", description = "OrderCreateDto object to be created", example = "clientId: 1, items: [{itemId: 1, quantity: 2}, {itemId: 2, quantity: 3}]")
    @PostMapping("/")
    public ResponseEntity<Long> addNewOrder(@RequestBody OrderCreateDto order) {
        return new ResponseEntity<>(orderService.create(order), HttpStatus.CREATED);
    }

    @Operation(summary = "Update order by order id", description = "Update a order certain parameters by order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not Found - The order id doesn't exist")
    })
    @PatchMapping(path = "{orderID}")
    public ResponseEntity<String> updateRegion(@PathVariable("orderID") Long id, @Valid @RequestBody OrderUpdateDto order) {
        orderService.updateOrder(id, order);
        return new ResponseEntity<>("Order successfully updated", HttpStatus.OK);
    }


}
