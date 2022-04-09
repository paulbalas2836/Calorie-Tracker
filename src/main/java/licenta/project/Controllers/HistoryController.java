package licenta.project.Controllers;

import licenta.project.Dto.FoodDto;
import licenta.project.Dto.GetHistoryDto;
import licenta.project.Dto.SaveHistoryDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Services.HistoryService;
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
@RequestMapping(path="/history")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost/3000")
public class HistoryController {
    private HistoryService historyService;

    @GetMapping(path = "getHistory/{email}")
    private ResponseEntity getHistoryByDay(@PathVariable String email){
       return new ResponseEntity(historyService.getHistory(email), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/prediction")
    public ResponseEntity<FoodDto> prediction(@Valid @NotEmpty @NotNull @RequestPart("image") MultipartFile image, @RequestPart("saveHistoryDto") SaveHistoryDto saveHistoryDto) throws AppException, IOException {
        return new ResponseEntity<>(historyService.getDataFromPrediction(image, saveHistoryDto), HttpStatus.ACCEPTED);
    }
}
