package backend.Wine_Project.dto.ratingDto;

import jakarta.validation.constraints.*;

public record RatingCreateDto(
        @Min(value = 1, message = "Client Id must be greater than 0")
        @NotNull(message = "Client Id is mandatory!")
        Long clientId,
        @NotNull(message = "Wine Id is mandatory!")
        @Min(value = 1, message = "Wine Id must be greater than 0")
        Long wineId,
        @NotNull(message = "Rate is mandatory!")
        @Min(value = 0, message = "Rate must be greater than 0")
        @Max(value = 5, message = "Rate must be less than 5")
        Double rate
) {
}
