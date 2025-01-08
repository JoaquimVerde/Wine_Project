package backend.Wine_Project.controller;

import backend.Wine_Project.dto.ratingDto.RatingCreateDto;
import backend.Wine_Project.dto.ratingDto.RatingReadDto;
import backend.Wine_Project.service.ratingService.RatingServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get all ratings", description = "Returns all ratings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieve")
    @GetMapping("/")
    public ResponseEntity<List<RatingReadDto>> getRatings() {
        return new ResponseEntity<>(ratingServiceImp.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Create a new rating", description = "Create a new rating with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The rating already existed")
    })
    @Parameter(name = "rating", description = "RatingCreateDto object to be created", example = "clientId: 1, wineId: 1, score: 5")
    @PostMapping("/")
    public ResponseEntity<Long> addNewRating(@Valid @RequestBody RatingCreateDto rating) {
        return new ResponseEntity<>(ratingServiceImp.create(rating), HttpStatus.CREATED);
    }

}
