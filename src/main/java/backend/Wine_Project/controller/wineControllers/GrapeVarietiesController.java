package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.service.wineService.GrapeVarietiesService;
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
    @GetMapping("/")
    public ResponseEntity<Set<GrapeVarietiesDto>> getGrapeVarieties(){
        return new ResponseEntity<>(grapeVarietiesService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<String> addNewGrapeVariety(@RequestBody GrapeVarietiesDto grapeVarietiesDto){
        grapeVarietiesService.create(grapeVarietiesDto);
        return new ResponseEntity<>("New grape variety added successfully", HttpStatus.CREATED);
    }
}
