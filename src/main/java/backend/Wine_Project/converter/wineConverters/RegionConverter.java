package backend.Wine_Project.converter.wineConverters;

import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.dto.regionDto.RegionDto;

import java.util.List;

public class RegionConverter {

    public static RegionDto fromRegionToRegionDto(Region region){
        return new RegionDto(
                region.getName(),
                region.getWine()
        );
    }
    public static Region fromRegionDtoToRegion(RegionDto regionDto){
        return new Region(
                regionDto.name(),
                regionDto.wine()
        );
    }
    public static List<RegionDto> fromRegionListToRegionDtoList(List<Region> regionList){
        return regionList.stream()
                .map(RegionConverter::fromRegionToRegionDto)
                .toList();
    }
}
