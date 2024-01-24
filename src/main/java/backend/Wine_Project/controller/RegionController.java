package backend.Wine_Project.controller;

import backend.Wine_Project.regionDto.RegionDto;
import backend.Wine_Project.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/regions")
public class RegionController {
    private final RegionService regionService;
    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RegionDto>> getRegions(){
        return new ResponseEntity<>(regionService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<String> addNewRegion(@RequestBody RegionDto regionDto){
        regionService.create(regionDto);
        return new ResponseEntity<>("Region added successfully",HttpStatus.CREATED);
    }


}
