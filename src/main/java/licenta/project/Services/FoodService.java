package licenta.project.Services;

import licenta.project.Dto.FoodDto;
import licenta.project.Models.Food;
import licenta.project.Repositories.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodService {

    private FoodRepository foodRepository;

    public Optional<Food> getFood(String label) {
        return this.foodRepository.getFoodByName(label);
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
