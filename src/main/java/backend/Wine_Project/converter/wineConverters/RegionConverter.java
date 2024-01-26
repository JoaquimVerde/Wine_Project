package backend.Wine_Project.converter.wineConverters;

import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.dto.regionDto.RegionCreateDto;

import java.util.List;

public class RegionConverter {

    public static RegionCreateDto fromRegionToRegionDto(Region region){
        return new RegionCreateDto(
                region.getName()
        );
    }
    /*public static Region fromRegionDtoToRegion(RegionCreateDto regionCreateDto){
        return new Region(
                regionCreateDto.name(),
                regionCreateDto.wine()
        );
    }*/
    public static List<RegionCreateDto> fromRegionListToRegionDtoList(List<Region> regionList){
        return regionList.stream()
                .map(RegionConverter::fromRegionToRegionDto)
                .toList();
    }
}
