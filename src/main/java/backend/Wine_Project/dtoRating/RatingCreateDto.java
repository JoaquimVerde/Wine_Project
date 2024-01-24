package backend.Wine_Project.dtoRating;

public record RatingCreateDto(

        Long clientId,
        Long wineId,
        double rate
) {
}
