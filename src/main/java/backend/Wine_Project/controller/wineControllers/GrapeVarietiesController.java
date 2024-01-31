package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.service.wineService.GrapeVarietiesService;
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
    public ResponseEntity<String> addNewGrapeVariety(@RequestBody GrapeVarietiesDto grapeVarietiesDto){
        grapeVarietiesService.create(grapeVarietiesDto);
        return new ResponseEntity<>("New grape variety added successfully", HttpStatus.CREATED);
    }
    @Operation(summary = "Create a multiple grape varieties", description = "Create multiple grape varieties with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One of the grape variety already existed")})
    @PostMapping("/addGrapeVarieties")
    public ResponseEntity<List<GrapeVarietiesDto>> addNewGrapeVarieties(@RequestBody List<GrapeVarietiesDto> grapeVarieties) {
        return new ResponseEntity<>(grapeVarietiesService.createGrapeVarieties(grapeVarieties), HttpStatus.CREATED);
    }
}
