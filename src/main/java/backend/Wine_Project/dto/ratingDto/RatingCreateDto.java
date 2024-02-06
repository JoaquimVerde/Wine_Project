package backend.Wine_Project.dto.ratingDto;

import jakarta.validation.constraints.*;

public record RatingCreateDto(

        @NotNull(message = "Client Id is mandatory!")
        Long clientId,
        @NotNull(message = "Wine Id is mandatory!")
        Long wineId,
//        @Digits(integer = 1, fraction = 2, message = "Rate must have 1 integer and 1 fraction")
        @Min(value = 0, message = "Rate must be greater than 0")
        @Max(value = 5, message = "Rate must be less than 5")
        Double rate
) {
}
