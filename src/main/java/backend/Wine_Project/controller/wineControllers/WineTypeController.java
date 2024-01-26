package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.service.wineService.WineTypeService;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
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
    @GetMapping("/")
    public ResponseEntity<List<WineTypeCreateDto>> getWineTypes(){
       return new ResponseEntity<>(wineTypeService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<String> createWineType(@RequestBody WineTypeCreateDto wineTypeCreateDto){
        wineTypeService.create(wineTypeCreateDto);
        return new ResponseEntity<>("Wine type created successfully", HttpStatus.CREATED);
    }

    @GetMapping(path = "{wineTypeId}")
    public ResponseEntity<Set<WineReadDto>> getWinesByType(@PathVariable("wineTypeId") Long wineTypeId) {
        return new ResponseEntity<>(wineTypeService.getWinesByType(wineTypeId), HttpStatus.OK);
    }


}
