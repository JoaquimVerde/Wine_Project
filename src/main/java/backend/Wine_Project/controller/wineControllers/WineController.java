package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.service.wineService.WineServiceImp;
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
    @GetMapping("/")
    public ResponseEntity<List<WineReadDto>> getWines(){
       return new ResponseEntity<>(wineService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<String> createWines(@RequestBody WineCreateDto wineCreateDto){
        wineService.create(wineCreateDto);
        return new ResponseEntity<>("Wine created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/search")
    public ResponseEntity<Set<WineReadDto>> searchWines(@RequestParam(required = false)String name, @RequestParam int year,
                                                        @RequestParam(required = false) Long wineTypeId){
        return new ResponseEntity<>(wineService.search(name, year, wineTypeId), HttpStatus.OK);

    }


    @DeleteMapping(path = "{wineId}")
    public ResponseEntity<String> deleteWine(@PathVariable("wineId") Long wineId) {
        wineService.deleteWine(wineId);
        return new ResponseEntity<>("Wine successfully deleted", HttpStatus.OK);
    }

    @PatchMapping(path = "{wineID}")
    public ResponseEntity<String> updateWine(@PathVariable("wineID") Long id, @Valid @RequestBody WineUpdateDto wine) {
        wineService.updateWine(id, wine);
        return new ResponseEntity<>("Wine successfully updated", HttpStatus.OK);
    }

    @PostMapping("/addWines")
    public ResponseEntity<List<WineCreateDto>> addNewWines(@RequestBody List<WineCreateDto> wines) {
        return new ResponseEntity<>(wineService.createWines(wines), HttpStatus.CREATED);
    }

}
