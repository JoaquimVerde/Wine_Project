package backend.Wine_Project.controller;

import backend.Wine_Project.service.WineServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WineController {

    private final WineServiceImp wineService;

    @Autowired
    public WineController(WineServiceImp wineService){
        this.wineService = wineService;
    }
    @GetMapping
    public ResponseEntity<String> getWines(){
        wineService.getAll();
        return null;
    }



}
