package licenta.project.Services;

import licenta.project.Dto.FoodDto;
import licenta.project.Dto.GetHistoryDto;
import licenta.project.Dto.SaveHistoryDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.AppUser;
import licenta.project.Models.Food;
import licenta.project.Models.History;
import licenta.project.Repositories.HistoryRepository;
import licenta.project.Utils.ImageManipulation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Getter
@AllArgsConstructor
public class HistoryService {
    private HistoryRepository historyRepository;
    private AppUserService appUserService;
    private ImageManipulation imageManipulation;
    private FoodService foodService;

    public FoodDto getDataFromPrediction(MultipartFile image, SaveHistoryDto saveHistoryDto) throws AppException {
        Optional<Food> defaultFood = foodService.getFood(saveHistoryDto.getLabel());
        if (defaultFood.isEmpty()) throw new AppException("No food found");
        FoodDto foodDto = new FoodDto(saveHistoryDto.getLabel(), saveHistoryDto.getWeight());
        if (foodDto.getQuantity() == null)
            foodDto.setQuantity(defaultFood.get().getDefaultQuantity());

        String filename = imageManipulation.saveImage(image, saveHistoryDto.getLabel());

        if (appUserService.emailExists(saveHistoryDto.getEmail())) {
            addToHistory(saveHistoryDto.getEmail(), defaultFood.get(), filename, foodDto.getQuantity());
        }
        return foodService.calculateNutritionalValues(defaultFood.get(), foodDto);
    }

    public void addToHistory(String email, Food food, String filename, Double quantity)
    {
        History history = new History();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);
        history.setAppUser(appUser);
        history.setFood(food);
        history.setQuantity(quantity);
        history.setPath("D:\\Licenta\\Licenta\\TrainImages\\" + filename + ".jpg");
        historyRepository.save(history);
    }

    public GetHistoryDto getHistory(String email){
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);
        List<Food> foodSet = foodService.getFoodByHistory(appUser.getAppUserHistorySet());
        return null;
    }


}
