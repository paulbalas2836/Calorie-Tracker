package licenta.project.Services;

import licenta.project.Dto.FoodDto;
import licenta.project.Dto.HistoryDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.Food;
import licenta.project.Repositories.FoodRepository;
import licenta.project.Utils.ImageManipulation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodService {

    private FoodRepository foodRepository;
    private AppUserService appUserService;
    private ImageManipulation imageManipulation;

    public FoodDto getData(MultipartFile image, HistoryDto historyDto) throws AppException, IOException {
        Optional<Food> defaultFood = this.foodRepository.getFoodByName(historyDto.getLabel());
        if (defaultFood.isEmpty()) throw new AppException("No food found");
        FoodDto foodDto = new FoodDto(historyDto.getLabel(), historyDto.getWeight());
        if (foodDto.getQuantity() == null)
            foodDto.setQuantity(defaultFood.get().getDefaultQuantity());
        return calculateNutritionalValues(defaultFood.get(), foodDto);
    }

    public FoodDto calculateNutritionalValues(Food food, FoodDto foodDto) {
        foodDto.setCalcium(round(((food.getCalcium() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setCalories(round(((food.getCalories() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setCarbs(round(((food.getCarbs() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setCholesterol(round(((food.getCholesterol() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setFat(round(((food.getFat() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setFiber(round(((food.getFiber() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setPotassium(round(((food.getPotassium() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setIron(round(((food.getIron() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setProtein(round(((food.getProtein() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        foodDto.setSodium(round(((food.getSodium() * foodDto.getQuantity()) / food.getDefaultQuantity())));
        return foodDto;
    }

    private static double round(double value) {
        int scale = (int) Math.pow(10, 2);
        return (double) Math.round(value * scale) / scale;
    }

}
