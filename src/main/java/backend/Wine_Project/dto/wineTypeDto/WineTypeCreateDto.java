package backend.Wine_Project.dto.wineTypeDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record WineTypeCreateDto(
        @NotNull
        @NotBlank(message = "Insert a valid wineType name")
        @Pattern(regexp = "^[\\w\\sáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ]+$", message = "Insert a valid wineType name")
        String name
) {
}
