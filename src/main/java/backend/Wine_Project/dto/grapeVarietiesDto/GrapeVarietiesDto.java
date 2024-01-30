package backend.Wine_Project.dto.grapeVarietiesDto;


import backend.Wine_Project.util.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GrapeVarietiesDto(
        @NotBlank(message = "Insert a valid grape variety name")
        @NotNull
        String name

) {
}
