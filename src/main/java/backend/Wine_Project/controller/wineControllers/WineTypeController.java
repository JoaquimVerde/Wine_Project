package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.service.wineService.WineTypeService;
import backend.Wine_Project.dto.wineTypeDto.WineTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/wineTypes")
public class WineTypeController{
    private final WineTypeService wineTypeService;

    @Autowired
    public WineTypeController(WineTypeService wineTypeService){
        this.wineTypeService = wineTypeService;
    }
    @GetMapping("/")
    public ResponseEntity<List<WineTypeDto>> getWineTypes(){
       return new ResponseEntity<>(wineTypeService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<String> createWineType(@RequestBody WineTypeDto wineTypeDto){
        wineTypeService.create(wineTypeDto);
        return new ResponseEntity<>("Wine type created successfully", HttpStatus.CREATED);
    }


}
