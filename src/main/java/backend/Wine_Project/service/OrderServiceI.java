package backend.Wine_Project.service;

import backend.Wine_Project.orderDto.OrderCreateDto;
import backend.Wine_Project.orderDto.OrderGetDto;

public interface OrderServiceI extends CrudService<OrderGetDto,OrderCreateDto, Long>{
}
