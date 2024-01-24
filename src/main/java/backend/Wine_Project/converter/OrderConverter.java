package backend.Wine_Project.converter;

import backend.Wine_Project.model.Order;
import backend.Wine_Project.orderDto.OrderCreateDto;
import backend.Wine_Project.orderDto.OrderGetDto;

import java.util.List;

public class OrderConverter {

    public static OrderGetDto fromModelToOrderGetDto(Order order) {
        return new OrderGetDto(
                order.getClient().getId(),
                order.getTotalPrice()
        );
    }

    public static Order fromOrderCreateDtoToModel(OrderCreateDto orderCreateDto) {
        return new Order(
                orderCreateDto.totalPrice()
        );
    }

    public static List<OrderGetDto> fromModelListToOrederGetDtoList (List<Order> orders) {
        return orders.stream().map(OrderConverter::fromModelToOrderGetDto).toList();
    }

}
