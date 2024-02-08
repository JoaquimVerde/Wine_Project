package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
import backend.Wine_Project.service.wineService.GrapeVarietiesService;
import backend.Wine_Project.util.Messages;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("api/v1/grapeVarieties")
public class GrapeVarietiesController {
    private final GrapeVarietiesService grapeVarietiesService;

    @Autowired
    public GrapeVarietiesController(GrapeVarietiesService grapeVarietiesService) {
        this.grapeVarietiesService = grapeVarietiesService;
    }

    @Operation(summary = "Get all grape varieties", description = "Returns all grape varieties names")
    @ApiResponse(responseCode = "200", description = "Successfully retrieve")
    @GetMapping("/")
    public ResponseEntity<Set<GrapeVarietiesDto>> getGrapeVarieties(){
        return new ResponseEntity<>(grapeVarietiesService.getAll(), HttpStatus.OK);
    }
    @Operation(summary = "Create a new grape variety", description = "Create a new grape variety with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The grape variety already existed")
    })
    @PostMapping("/")
    public ResponseEntity<String> addNewGrapeVariety(@Valid @RequestBody GrapeVarietiesDto grapeVarietiesDto){
        grapeVarietiesService.create(grapeVarietiesDto);
        return new ResponseEntity<>(Messages.GRAPE_VARIETY_CREATED.getMessage(), HttpStatus.CREATED);
    }
    @Operation(summary = "Create a multiple grape varieties", description = "Create multiple grape varieties with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One of the grape variety already existed")})
    @PostMapping("/addGrapeVarieties")
    public ResponseEntity<List<GrapeVarietiesDto>> addNewGrapeVarieties(@RequestBody List<GrapeVarietiesDto> grapeVarieties) {
        return new ResponseEntity<>(grapeVarietiesService.createGrapeVarieties(grapeVarieties), HttpStatus.CREATED);
    }

    @Operation(summary = "Update grapeVariety by grapeVariety id", description = "Update a grape variety certain parameters by grapeVariety id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not Found - The grapeVariety id doesn't exist")
    })
    @PatchMapping(path = "{grapeVarietyID}")
    public ResponseEntity<String> updateGrapeVariety(@PathVariable("grapeVarietyID") Long id, @Valid @RequestBody GrapeVarietiesDto grape) {
        grapeVarietiesService.updateGrapeVariety(id, grape);
        return new ResponseEntity<>("Grape Variety successfully updated", HttpStatus.OK);
    }
}
