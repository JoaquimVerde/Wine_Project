package backend.Wine_Project.dto.grapeVarietiesDto;


import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.util.Messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.builder.ToStringExclude;

public record GrapeVarietiesDto(
        @NotBlank(message = "Insert a valid grape variety name")
        @NotNull
        @Pattern(regexp = "^[\\w\\sáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ]+$", message = "insert a valid name")
        String name

) {

}
