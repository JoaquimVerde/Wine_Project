package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.service.wineService.WineTypeService;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
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
@RequestMapping("api/v1/wineTypes")
public class WineTypeController{
    private final WineTypeService wineTypeService;

    @Autowired
    public WineTypeController(WineTypeService wineTypeService){
        this.wineTypeService = wineTypeService;
    }
    @Operation(summary = "Get all wine types", description = "Returns all the wine types")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @GetMapping("/")
    public ResponseEntity<List<WineTypeCreateDto>> getWineTypes(){
       return new ResponseEntity<>(wineTypeService.getAll(), HttpStatus.OK);
    }
    @Operation(summary = "Create a new wine type", description = "Create a wine type with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The grape variety already existed")
    })
    @PostMapping("/")
    public ResponseEntity<String> createWineType(@Valid @RequestBody WineTypeCreateDto wineTypeCreateDto){
        wineTypeService.create(wineTypeCreateDto);
        return new ResponseEntity<>("Wine type created successfully", HttpStatus.CREATED);
    }
    @Operation(summary = "Get a wines by wine type id", description = "Returns a wines as per the wine type id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not Found - The wine type doesn't exist")
    })
    @GetMapping(path = "{wineTypeId}")
    public ResponseEntity<Set<WineReadDto>> getWinesByType(@PathVariable("wineTypeId") Long wineTypeId) {
        return new ResponseEntity<>(wineTypeService.getWinesByType(wineTypeId), HttpStatus.OK);
    }
    @Operation(summary = "Create a multiple wine types", description = "Create multiple wine types with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One of the wine type already existed")})
    @PostMapping("/addWineTypes")
    public ResponseEntity<List<WineTypeCreateDto>> addNewWineTypes(@Valid@RequestBody List<WineTypeCreateDto> wineTypes) {
        return new ResponseEntity<>(wineTypeService.createWineTypes(wineTypes), HttpStatus.CREATED);
    }


}
