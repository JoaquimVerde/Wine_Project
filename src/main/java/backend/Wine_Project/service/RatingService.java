package backend.Wine_Project.service;

import backend.Wine_Project.dtoRating.RatingCreateDto;
import backend.Wine_Project.dtoRating.RatingReadDto;

import java.util.List;

public interface RatingService {

    List<RatingReadDto> getAll();

    Long create(RatingCreateDto rating);

}


