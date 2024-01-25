package backend.Wine_Project.controller;

import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.dto.orderDto.OrderGetDto;
import backend.Wine_Project.service.orderService.OrderServiceImp;
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

    @GetMapping("/")
    public ResponseEntity<List<OrderGetDto>> getOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> addNewOrder(@RequestBody OrderCreateDto order) {
        return new ResponseEntity<>(orderService.create(order), HttpStatus.CREATED);
    }

}
