package backend.Wine_Project.service;

import backend.Wine_Project.dtoWine.WineCreateDto;
import backend.Wine_Project.model.Wine;


public interface WineService extends CrudService<WineCreateDto, WineCreateDto, Long> {
    Wine getById(Long id);
}
