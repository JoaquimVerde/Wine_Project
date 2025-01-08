package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.service.wineService.WineTypeService;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
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
    @Parameter(name = "wineType", description = "WineTypeCreateDto object to be created", example = "name: Red")
    @PostMapping("/")
    public ResponseEntity<String> createWineType(@Valid @RequestBody WineTypeCreateDto wineTypeCreateDto){
        wineTypeService.create(wineTypeCreateDto);
        return new ResponseEntity<>(Messages.WINE_TYPE_CREATED.getMessage(), HttpStatus.CREATED);
    }
    @Operation(summary = "Get a wines by wine type id", description = "Returns a wines as per the wine type id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not Found - The wine type doesn't exist")
    })
    @Parameter(name = "wineTypeId", description = "Wine type id to retrieve wines", example = "1")
    @GetMapping(path = "{wineTypeId}")
    public ResponseEntity<Set<WineReadDto>> getWinesByType(@PathVariable("wineTypeId") Long wineTypeId) {
        return new ResponseEntity<>(wineTypeService.getWinesByType(wineTypeId), HttpStatus.OK);
    }
    @Operation(summary = "Create a multiple wine types", description = "Create multiple wine types with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One of the wine type already existed")})
    @Parameter(name = "wineTypes", description = "List of WineTypeCreateDto objects to be created", example = "[name: Red, name: White]")
    @PostMapping("/addWineTypes")
    public ResponseEntity<List<WineTypeCreateDto>> addNewWineTypes(@Valid@RequestBody List<WineTypeCreateDto> wineTypes) {
        return new ResponseEntity<>(wineTypeService.createWineTypes(wineTypes), HttpStatus.CREATED);
    }

    @Operation(summary = "Update wineType by wineType id", description = "Update a wineType certain parameters by wineType id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not Found - The wineType id doesn't exist")
    })
    @Parameter(name = "wineTypeID", description = "WineType id to be updated",example = "1")
    @PatchMapping(path = "{wineTypeID}")
    public ResponseEntity<String> updateWineType(@PathVariable("wineTypeID") Long id, @Valid @RequestBody WineTypeCreateDto wine) {
        wineTypeService.updateWineType(id, wine);
        return new ResponseEntity<>("WineType successfully updated", HttpStatus.OK);
    }


}
