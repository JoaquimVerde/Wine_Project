package backend.Wine_Project.converter;

import backend.Wine_Project.model.WineType;
import backend.Wine_Project.wineTypeDto.WineTypeDto;

import java.util.List;

public class WineTypeConverter {
    public static WineTypeDto fromWineTypeToWineTypeDto(WineType wineType){
        return new WineTypeDto(wineType.getName(), wineType.getWine());
    }
    public static WineType fromWineTypeDtoToWineType(WineTypeDto wineTypeDto){
        return new WineType(wineTypeDto.name(),wineTypeDto.wine());
    }
    public static List<WineTypeDto> fromWineTypeListToWineTypeDtoList(List<WineType> wineTypeList){
        return wineTypeList.stream()
                .map(WineTypeConverter::fromWineTypeToWineTypeDto)
                .toList();
    }



}
