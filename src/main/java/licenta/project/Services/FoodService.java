package licenta.project.Services;

import licenta.project.Dto.DailyNutrientsDto;
import licenta.project.Dto.FoodDto;
import licenta.project.Models.Food;

import java.util.Optional;

public interface FoodService {
    Optional<Food> getFood(String label);

    FoodDto calculateNutritionalValues(Food food, FoodDto foodDto);

    void getDailyNutrients(DailyNutrientsDto dailyNutrientsDto, FoodDto foodDto);
}
