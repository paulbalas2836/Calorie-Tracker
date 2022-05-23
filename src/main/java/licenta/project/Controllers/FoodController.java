package licenta.project.Controllers;

import licenta.project.Dto.FoodDto.AddUpdateFoodDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.Food;
import licenta.project.Services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/food")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost/3000")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/getAllFoods")
    public ResponseEntity<List<Food>> getAllFoods() {
        return new ResponseEntity<>(foodService.getAllFoods(), HttpStatus.OK);
    }

    @PostMapping("/addFood")
    public ResponseEntity<?> addFood(@Valid @RequestBody AddUpdateFoodDto addUpdateFoodDto) {
        foodService.addFood(addUpdateFoodDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateFood/{foodId}")
    public ResponseEntity<?> updateFood(@PathVariable Long foodId, AddUpdateFoodDto addUpdateFoodDto) {
        try {
            foodService.updateFood(foodId, addUpdateFoodDto);
        } catch (AppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteFood/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long foodId) {
        try {
            foodService.deleteFood(foodId);
        } catch (AppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
