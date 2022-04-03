package licenta.project.Services;

import licenta.project.Dto.FoodDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.Food;
import licenta.project.Repositories.FoodRepository;
import licenta.project.Utils.ImageManipulation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodService {
    private FoodRepository foodRepository;
    private AppUserService appUserService;
    private ImageManipulation imageManipulation;

    public FoodDto getData(MultipartFile image, Double weight, String email, String label) throws AppException {
        Optional<Food> defaultFood = this.foodRepository.getFoodByName(label);
        if (defaultFood.isEmpty()) throw new AppException("No food found");
        FoodDto foodDto = new FoodDto(label, weight);
        return calculateNutritionalValues(defaultFood.get(), foodDto);
    }

    public FoodDto calculateNutritionalValues(Food food, FoodDto foodDto) {
        foodDto.setCalcium((food.getCalcium() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setCalories((food.getCalories() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setCarbs((food.getCarbs() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setCholesterol((food.getCholesterol() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setFat((food.getFat() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setFiber((food.getFiber() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setPotassium((food.getPotassium() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setIron((food.getIron() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setProtein((food.getProtein() * foodDto.getQuantity()) / food.getDefaultQuantity());
        foodDto.setSodium((food.getSodium() * foodDto.getQuantity()) / food.getDefaultQuantity());
        return foodDto;
    }

}
