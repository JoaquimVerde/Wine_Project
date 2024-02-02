package backend.Wine_Project.service.orderService;

import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.dto.orderDto.OrderGetDto;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.model.ShoppingCart;

import java.util.List;

public interface OrderService {
    List<OrderGetDto> getAll();

    Long create(OrderCreateDto order);

    void generatePdfInvoice(Order order);

    String printInvoice(ShoppingCart shoppingCart);
}
