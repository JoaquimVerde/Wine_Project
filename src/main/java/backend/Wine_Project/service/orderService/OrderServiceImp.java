package backend.Wine_Project.service.orderService;

import backend.Wine_Project.converter.OrderConverter;
import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.dto.orderDto.OrderGetDto;
import backend.Wine_Project.exceptions.ShoppingCartAlreadyBeenOrderedException;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.repository.OrderRepository;
import backend.Wine_Project.service.clientService.ClientService;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import backend.Wine_Project.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartServiceImp shoppingCartService;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository, ShoppingCartServiceImp shoppingCartService) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public List<OrderGetDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderConverter::fromModelToOrderGetDto).toList();
    }

    @Override
    public Long create(OrderCreateDto order) {


        ShoppingCart shoppingCart = shoppingCartService.getById(order.shoppingCartId());

        if (shoppingCart.isOrdered()) {
            throw new ShoppingCartAlreadyBeenOrderedException(Messages.SHOPPING_CART_ALREADY_ORDERED.getMessage());
        }

        Order newOrder = new Order(shoppingCart);

        shoppingCartService.closeShoppingCart(shoppingCart);

        orderRepository.save(newOrder);
        return newOrder.getId();

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, OrderGetDto modelUpdateDto) {

    }

    @Override
    public OrderGetDto get(Long id) {
        return null;
    }
}
