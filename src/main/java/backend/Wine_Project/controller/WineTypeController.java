package backend.Wine_Project.controller;

import backend.Wine_Project.model.WineType;
import backend.Wine_Project.service.WineTypeService;
import backend.Wine_Project.wineTypeDto.WineTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<WineTypeDto> getWineTypes(){
       return wineTypeService.getAll();
    }


}
