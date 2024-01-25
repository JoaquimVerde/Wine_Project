package backend.Wine_Project.controller;

import backend.Wine_Project.dto.ratingDto.RatingCreateDto;
import backend.Wine_Project.dto.ratingDto.RatingReadDto;
import backend.Wine_Project.service.ratingService.RatingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ratings")
public class RatingController {

    private final RatingServiceImp ratingServiceImp;
    @Autowired
    public RatingController(RatingServiceImp ratingServiceImp) {
        this.ratingServiceImp = ratingServiceImp;
    }

    @GetMapping("/")
    public ResponseEntity<List<RatingReadDto>> getRatings() {
        return new ResponseEntity<>(ratingServiceImp.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> addNewRating(@RequestBody RatingCreateDto rating) {
        return new ResponseEntity<>(ratingServiceImp.create(rating), HttpStatus.CREATED);
    }

}
