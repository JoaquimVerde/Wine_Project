package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
import backend.Wine_Project.service.wineService.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all regions", description = "Returns all regions names")
    @ApiResponse(responseCode = "200", description = "Successfully retrieve")
    @GetMapping("/")
    public ResponseEntity<List<RegionCreateDto>> getRegions(){
        return new ResponseEntity<>(regionService.getAll(), HttpStatus.OK);
    }
    @Operation(summary = "Create a new region", description = "Create a new region with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The region already existed")
    })
    @PostMapping("/")
    public ResponseEntity<String> addNewRegion(@RequestBody RegionCreateDto regionCreateDto){
        regionService.create(regionCreateDto);
        return new ResponseEntity<>("Region added successfully",HttpStatus.CREATED);
    }
    @Operation(summary = "Get region by id", description = "Returns a region as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve"),
            @ApiResponse(responseCode = "404", description = "Not found - The region was not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request - You didn't given a valid id")
    })

    @GetMapping(path = "{regionId}")
    public ResponseEntity<Set<WineReadDto>> getWinesByRegionId(@PathVariable("regionId") Long regionId) {
        return new ResponseEntity<>(regionService.getWinesByRegion(regionId), HttpStatus.OK);
    }
    @Operation(summary = "Create a multiple regions", description = "Create multiple regions with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One of the regions already existed")})
    @PostMapping("/addRegions")
    public ResponseEntity<List<RegionCreateDto>> addNewRegions(@RequestBody List<RegionCreateDto> regions) {
        return new ResponseEntity<>(regionService.createRegions(regions), HttpStatus.CREATED);
    }


}
