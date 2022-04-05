package licenta.project.Controllers;

import licenta.project.Exceptions.AppException;
import licenta.project.Services.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping(path = "/food")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost/3000")
public class FoodController {
    private FoodService foodService;

    @PostMapping(path = "/prediction")
    public ResponseEntity prediction(@Valid @NotEmpty @NotNull @RequestPart("image") MultipartFile image,@RequestPart("weight") String weight, @RequestPart("email") String email, @RequestPart("label") String label) throws AppException, IOException {
        return new ResponseEntity(foodService.getData(image, Double.parseDouble(weight), email, label), HttpStatus.ACCEPTED);
    }
}
