package backend.Wine_Project.dto.wineDto;

import java.util.Set;

public record WineUpdateDto(

        String name,
        Long wineTypeId,
        Set<Long> grapeVarietiesId,
        Long regionId,
        double price,
        double alcohol,
        int year
) {
}
