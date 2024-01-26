package backend.Wine_Project.dto.clientDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public record ClientCreateDto(

        String name,
        @Valid
        @Email
        String email,
        int nif

) {
}
