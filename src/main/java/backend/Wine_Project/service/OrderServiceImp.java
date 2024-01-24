package backend.Wine_Project.service;

import backend.Wine_Project.converter.OrderConverter;
import backend.Wine_Project.exceptions.OrderALreadyExistsException;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.orderDto.OrderCreateDto;
import backend.Wine_Project.orderDto.OrderGetDto;
import backend.Wine_Project.repository.OrderRepository;
import backend.Wine_Project.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderServiceI{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderGetDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderConverter::fromModelToOrderGetDto).toList();
    }

    @Override
    public Long create(OrderCreateDto order) {
        Optional<Order> orderOptional = this.orderRepository.findById(order.clientId());
        if (orderOptional.isPresent())
            throw new OrderALreadyExistsException(Messages.ORDER_ALREADY_EXISTS.getMessage());
        Order newOrder = OrderConverter.fromOrderCreateDtoToModel(order);
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
