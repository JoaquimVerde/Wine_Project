package backend.Wine_Project.controller;

import backend.Wine_Project.dtoWine.WineCreateDto;
import backend.Wine_Project.service.WineServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/wines")
public class WineController {

    private final WineServiceImp wineService;

    @Autowired
    public WineController(WineServiceImp wineService){
        this.wineService = wineService;
    }
    @GetMapping("/")
    public ResponseEntity<List<WineCreateDto>> getWines(){
       return new ResponseEntity<>(wineService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<String> createWines(@RequestBody WineCreateDto wineCreateDto){
        wineService.create(wineCreateDto);
        return  new ResponseEntity<>("Wine created successfully", HttpStatus.CREATED);
    }




}
