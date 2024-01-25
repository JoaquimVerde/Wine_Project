package backend.Wine_Project.service.ratingService;

import backend.Wine_Project.dto.ratingDto.RatingCreateDto;
import backend.Wine_Project.dto.ratingDto.RatingReadDto;

import java.util.List;

public interface RatingService {

    List<RatingReadDto> getAll();

    Long create(RatingCreateDto rating);

}


