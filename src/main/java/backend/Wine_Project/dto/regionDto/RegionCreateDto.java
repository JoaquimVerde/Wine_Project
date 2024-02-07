package backend.Wine_Project.dto.regionDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegionCreateDto(
        @NotNull
        @NotBlank(message = "Insert a valid region name")
        //@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "insert a valid Region name")
        String name
) {
}
