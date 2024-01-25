package backend.Wine_Project.service.orderService;

import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.dto.orderDto.OrderGetDto;
import backend.Wine_Project.service.CrudService;

public interface OrderService extends CrudService<OrderGetDto,OrderCreateDto, Long> {
}
