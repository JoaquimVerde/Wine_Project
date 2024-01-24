package backend.Wine_Project.serviceTests;

import backend.Wine_Project.exceptions.OrderALreadyExistsException;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.orderDto.OrderCreateDto;
import backend.Wine_Project.orderDto.OrderGetDto;
import backend.Wine_Project.repository.OrderRepository;
import backend.Wine_Project.service.OrderServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImpTest {

    @InjectMocks
    private OrderServiceImp orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllOrdersReturnsExpectedOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderGetDto> orders = orderService.getAll();

        assertEquals(2, orders.size());
    }

    @Test
    public void createOrderSuccessfullyWhenOrderNotExists() {
        OrderCreateDto orderCreateDto = new OrderCreateDto(1L, 2.0);
        when(orderRepository.findById(orderCreateDto.clientId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> orderService.create(orderCreateDto));
    }

    @Test
    public void createOrderThrowsExceptionWhenOrderExists() {
        OrderCreateDto orderCreateDto = new OrderCreateDto(1L, 2.0);
        when(orderRepository.findById(orderCreateDto.clientId())).thenReturn(Optional.of(new Order()));

        assertThrows(OrderALreadyExistsException.class, () -> orderService.create(orderCreateDto));
    }
}