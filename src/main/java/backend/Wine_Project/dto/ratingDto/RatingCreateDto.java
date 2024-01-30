package backend.Wine_Project.dto.ratingDto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingCreateDto(

        @NotNull(message = "Client Id is mandatory!")
        Long clientId,
        @NotNull(message = "Wine Id is mandatory!")
        Long wineId,

        @Min(value = 0)
        @Max(value = 5)
        @Digits(integer = 5, fraction = 2)
        double rate
) {
}
