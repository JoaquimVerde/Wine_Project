package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.service.wineService.WineServiceImp;
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
@RequestMapping("api/v1/wines")
public class WineController {

    private final WineServiceImp wineService;

    @Autowired
    public WineController(WineServiceImp wineService){
        this.wineService = wineService;
    }
    @Operation(summary = "Get all wines", description = "Returns all wines names, region, wine type, etc ...")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @GetMapping(path = "{pageNumber}/{pageSize}")
    public ResponseEntity<List<WineReadDto>> getWines(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize){
       return new ResponseEntity<>(wineService.getAll(pageNumber, pageSize), HttpStatus.OK);
    }
    @Operation(summary = "Create a new wine ", description = "Create a wine with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - The wine already existed")
    })
    @PostMapping("/")
    public ResponseEntity<String> createWines(@RequestBody WineCreateDto wineCreateDto){
        wineService.create(wineCreateDto);
        return new ResponseEntity<>("Wine created successfully", HttpStatus.CREATED);
    }
    @Operation(summary = "Get wines with given parameters", description = "Get wines by wine name, year and wine type id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not Found - Didn't find a wine with given parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<Set<WineReadDto>> searchWines(@RequestParam(required = false)String name, @RequestParam int year,
                                                        @RequestParam(required = false) Long wineTypeId){
        return new ResponseEntity<>(wineService.search(name, year, wineTypeId), HttpStatus.OK);

    }
    @Operation(summary = "Delete a wine type by id", description = "Delete a wine as per the wine id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not Found - The wine id doesn't exist")
    })
    @DeleteMapping(path = "{wineId}")
    public ResponseEntity<String> deleteWine(@PathVariable("wineId") Long wineId) {
        wineService.deleteWine(wineId);
        return new ResponseEntity<>("Wine successfully deleted", HttpStatus.OK);
    }
    @Operation(summary = "Update wine by wine id", description = "Update a wine certain parameters by wine id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not Found - The wine id doesn't exist")
    })
    @PatchMapping(path = "{wineID}")
    public ResponseEntity<String> updateWine(@PathVariable("wineID") Long id, @Valid @RequestBody WineUpdateDto wine) {
        wineService.updateWine(id, wine);
        return new ResponseEntity<>("Wine successfully updated", HttpStatus.OK);
    }
    @Operation(summary = "Create a multiple wines", description = "Create multiple wines with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Conflict - One of the wines already existed")})
    @PostMapping("/addWines")
    public ResponseEntity<List<WineCreateDto>> addNewWines(@RequestBody List<WineCreateDto> wines) {
        return new ResponseEntity<>(wineService.createWines(wines), HttpStatus.CREATED);
    }

}
