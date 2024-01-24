package backend.Wine_Project.service;

import backend.Wine_Project.converter.OrderConverter;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.orderDto.OrderCreateDto;
import backend.Wine_Project.orderDto.OrderGetDto;
import backend.Wine_Project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderServiceI{

    private final OrderRepository orderRepository;
    private final ClientService clientService;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
    }

    @Override
    public List<OrderGetDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return OrderConverter.fromModelListToOrederGetDtoList(orders);
    }

    @Override
    public Long create(OrderCreateDto order) {

        Client client = clientService.getById(order.clientId());

        Order newOrder = new Order(client, order.totalPrice());
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
