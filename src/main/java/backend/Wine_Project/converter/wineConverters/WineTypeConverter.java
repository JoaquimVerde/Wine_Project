package backend.Wine_Project.converter.wineConverters;

import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;

import java.util.List;

public class WineTypeConverter {
    public static WineTypeCreateDto fromWineTypeToWineTypeDto(WineType wineType){
        return new WineTypeCreateDto(wineType.getName());
    }

    public static List<WineTypeCreateDto> fromWineTypeListToWineTypeDtoList(List<WineType> wineTypeList){
        return wineTypeList.stream()
                .map(WineTypeConverter::fromWineTypeToWineTypeDto)
                .toList();
    }



}
