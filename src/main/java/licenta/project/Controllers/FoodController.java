package licenta.project.Controllers;

import licenta.project.Services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/food")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost/3000")
public class FoodController {

    private final FoodService foodService;
}
