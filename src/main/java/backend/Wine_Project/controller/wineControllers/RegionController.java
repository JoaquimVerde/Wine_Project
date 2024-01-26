package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.service.wineService.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/regions")
public class RegionController {
    private final RegionService regionService;
    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RegionCreateDto>> getRegions(){
        return new ResponseEntity<>(regionService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<String> addNewRegion(@RequestBody RegionCreateDto regionCreateDto){
        regionService.create(regionCreateDto);
        return new ResponseEntity<>("Region added successfully",HttpStatus.CREATED);
    }

    @GetMapping(path = "{regionId}")
    public ResponseEntity<Set<WineReadDto>> getWinesByRegionId(@PathVariable("regionId") Long regionId) {
        return new ResponseEntity<>(regionService.getWinesByRegion(regionId), HttpStatus.OK);
    }


}
