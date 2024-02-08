package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.service.wineService.RegionService;
import backend.Wine_Project.util.Messages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<RegionCreateDto>> getRegions() {
        return new ResponseEntity<>(regionService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Create a new region", description = "Create a new region with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The region already existed")
    })
    @Parameter(name = "region", description = "RegionCreateDto object to be created", example = "name: Douro")
    @PostMapping("/")
    public ResponseEntity<String> addNewRegion(@Valid @RequestBody RegionCreateDto regionCreateDto) {
        regionService.create(regionCreateDto);
        return new ResponseEntity<>(Messages.REGION_CREATED.getMessage(), HttpStatus.CREATED);
    }

    @Operation(summary = "Get wines by region id", description = "Returns wines as per the region id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve"),
            @ApiResponse(responseCode = "404", description = "Not found - The region was not found"),
    })
    @Parameter(name = "regionId", description = "Region id to retrieve wines", example = "1")
    @GetMapping(path = "{regionId}")
    public ResponseEntity<Set<WineReadDto>> getWinesByRegionId(@PathVariable("regionId") Long regionId) {
        return new ResponseEntity<>(regionService.getWinesByRegion(regionId), HttpStatus.OK);
    }

    @Operation(summary = "Create a multiple regions", description = "Create multiple regions with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One of the regions already existed")})
    @Parameter(name = "regions", description = "List of RegionCreateDto objects to be created", example = "[name: Douro, name: Alentejo]")
    @PostMapping("/addRegions")
    public ResponseEntity<List<RegionCreateDto>> addNewRegions(@Valid @RequestBody List<RegionCreateDto> regions) {
        return new ResponseEntity<>(regionService.createRegions(regions), HttpStatus.CREATED);
    }

    @Operation(summary = "Update region by region id", description = "Update a region certain parameters by region id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not Found - The region id doesn't exist")
    })
    @Parameter(name = "regionID", description = "Region id to be updated", example = "1")
    @PatchMapping(path = "{regionID}")
    public ResponseEntity<String> updateRegion(@PathVariable("regionID") Long id, @Valid @RequestBody RegionCreateDto region) {
        regionService.updateRegion(id, region);
        return new ResponseEntity<>("Region successfully updated", HttpStatus.OK);
    }


}
