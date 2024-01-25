package backend.Wine_Project.dto.ratingDto;

public record RatingCreateDto(

        Long clientId,
        Long wineId,
        double rate
) {
}
