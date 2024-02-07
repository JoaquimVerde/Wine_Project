package backend.Wine_Project.dto.regionDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegionCreateDto(
        @NotNull
        @NotBlank(message = "Insert a valid region name")
        @Pattern(regexp = "^[\\w\\sáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ]+$", message = "Insert a valid region name")
        String name
) {
}
