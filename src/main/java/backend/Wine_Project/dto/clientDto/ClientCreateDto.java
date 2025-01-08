package backend.Wine_Project.dto.clientDto;


import jakarta.validation.constraints.*;

public record ClientCreateDto(

        @NotEmpty(message = "You need to insert a name!")
        @Size(min = 2, message = "Your name must be 2 or more characters!")
        String name,

        @Email(message = "Your email isn`t valid")
        @NotEmpty(message = "You need to insert a email!")
        String email,

        @Min(value = 100000000, message = "Invalid nif")
        @Max(value = 999999999, message = "Invalid nif")
        int nif

) {
}
