package backend.Wine_Project.converter;

import backend.Wine_Project.dto.orderDto.OrderCreateDto;

import backend.Wine_Project.dto.orderDto.OrderGetDto;
import backend.Wine_Project.model.Order;

public class OrderConverter {

    public static OrderGetDto fromModelToOrderGetDto(Order order) {
        return new OrderGetDto(
                ShoppingCartConverter.fromModelToCartGetDto(order.getShoppingCart()),
                order.getTotalPrice()
        );
    }


}
