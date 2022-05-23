package licenta.project.Services.ServiceImpl;

import licenta.project.Dto.FoodDto.AddUpdateFoodDto;
import licenta.project.Dto.HistoryDto.DailyNutrientsDto;
import licenta.project.Dto.HistoryDto.FoodHistoryDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.Food;
import licenta.project.Repositories.FoodRepository;
import licenta.project.Services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    public Optional<Food> getFood(String label) {
        return foodRepository.getFoodByName(label);
    }

    public Boolean foodExist(Long foodId) {
        return foodRepository.findById(foodId).isPresent();
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public void addFood(AddUpdateFoodDto addUpdateFoodDto) {
        Food food = new Food(addUpdateFoodDto.getName(), addUpdateFoodDto.getCalories(), addUpdateFoodDto.getProtein(),
                addUpdateFoodDto.getFat(), addUpdateFoodDto.getCarbs(), addUpdateFoodDto.getFiber(),
                addUpdateFoodDto.getCalcium(), addUpdateFoodDto.getCholesterol(), addUpdateFoodDto.getPotassium(),
                addUpdateFoodDto.getSodium(), addUpdateFoodDto.getIron(), addUpdateFoodDto.getQuantity());

        foodRepository.save(food);
    }

    @Override
    public void updateFood(Long foodId, AddUpdateFoodDto addUpdateFoodDto) throws AppException {
        if (foodExist(foodId)) {
            Food food = foodRepository.getById(foodId);
            food.setName(addUpdateFoodDto.getName());
            food.setCalcium(addUpdateFoodDto.getCalcium());
            food.setCalories(addUpdateFoodDto.getCalories());
            food.setCarbs(addUpdateFoodDto.getCarbs());
            food.setCholesterol(addUpdateFoodDto.getCholesterol());
            food.setFat(addUpdateFoodDto.getFat());
            food.setFiber(addUpdateFoodDto.getFiber());
            food.setPotassium(addUpdateFoodDto.getPotassium());
            food.setIron(addUpdateFoodDto.getIron());
            food.setProtein(addUpdateFoodDto.getProtein());
            food.setSodium(addUpdateFoodDto.getSodium());

            foodRepository.save(food);
        } else {
            throw new AppException("No food found!");
        }
    }

    @Override
    public void deleteFood(Long foodId) throws AppException {
        if (foodExist(foodId)) {
            Food food = foodRepository.getById(foodId);
            foodRepository.delete(food);
        } else {
            throw new AppException("No food found!");
        }
    }

    public FoodHistoryDto calculateNutritionalValues(Food food, FoodHistoryDto foodHistoryDto) {
        foodHistoryDto.setCalcium(round((food.getCalcium() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setCalories(round((food.getCalories() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setCarbs(round((food.getCarbs() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setCholesterol(round((food.getCholesterol() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setFat(round((food.getFat() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setFiber(round((food.getFiber() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setPotassium(round((food.getPotassium() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setIron(round((food.getIron() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setProtein(round((food.getProtein() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        foodHistoryDto.setSodium(round((food.getSodium() * foodHistoryDto.getQuantity()) / food.getDefaultQuantity()));
        return foodHistoryDto;
    }

    public void getDailyNutrients(DailyNutrientsDto dailyNutrientsDto, FoodHistoryDto foodHistoryDto) {
        dailyNutrientsDto.setCalcium(round(dailyNutrientsDto.getCalcium() + foodHistoryDto.getCalcium()));
        dailyNutrientsDto.setCalories(round(dailyNutrientsDto.getCalories() + foodHistoryDto.getCalories()));
        dailyNutrientsDto.setCarbs(round(dailyNutrientsDto.getCarbs() + foodHistoryDto.getCarbs()));
        dailyNutrientsDto.setCholesterol(round(dailyNutrientsDto.getCholesterol() + foodHistoryDto.getCholesterol()));
        dailyNutrientsDto.setFiber(round(dailyNutrientsDto.getFiber() + foodHistoryDto.getFiber()));
        dailyNutrientsDto.setFat(round(dailyNutrientsDto.getFat() + foodHistoryDto.getFat()));
        dailyNutrientsDto.setIron(round(dailyNutrientsDto.getIron() + foodHistoryDto.getIron()));
        dailyNutrientsDto.setPotassium(round(dailyNutrientsDto.getPotassium() + foodHistoryDto.getPotassium()));
        dailyNutrientsDto.setProtein(round(dailyNutrientsDto.getProtein() + foodHistoryDto.getProtein()));
        dailyNutrientsDto.setSodium(round(dailyNutrientsDto.getSodium() + foodHistoryDto.getSodium()));

    }

    static double round(double value) {
        int scale = (int) Math.pow(10, 2);
        return (double) Math.round(value * scale) / scale;
    }

}
