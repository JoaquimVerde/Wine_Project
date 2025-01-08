package backend.Wine_Project.dto.grapeVarietiesDto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record GrapeVarietiesDto(
        @NotBlank(message = "Insert a valid grape variety name")
        @NotNull
        @Pattern(regexp = "^[\\w\\sáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ]+$", message = "Insert a valid grape variety name")
        String name

) {

}
