package licenta.project.Controllers;

import licenta.project.Dto.FoodDto.AddUpdateFoodDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Records.FoodRecord;
import licenta.project.Services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/food")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost/3000")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/getAll")
    public ResponseEntity<Page<FoodRecord>> getAllFoods(@RequestParam(required = false) Integer page) throws AppException {
        return new ResponseEntity<>(foodService.getAllFoods(page), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addFood(@Valid @RequestBody AddUpdateFoodDto addUpdateFoodDto) {
        foodService.addFood(addUpdateFoodDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{foodId}")
    public ResponseEntity<?> updateFood(@PathVariable Long foodId, @Valid @RequestBody AddUpdateFoodDto addUpdateFoodDto) {
        try {
            foodService.updateFood(foodId, addUpdateFoodDto);
        } catch (AppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long foodId) {
        try {
            foodService.deleteFood(foodId);
        } catch (AppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
