package licenta.project.Services;

import licenta.project.Dto.DailyNutrientsDto;
import licenta.project.Dto.FoodDto;
import licenta.project.Dto.HistoryIntervalDto;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public void addToHistory(String email, Food food, String filename, Double quantity) {
        History history = new History();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);
        history.setAppUser(appUser);
        history.setFood(food);
        history.setQuantity(quantity);
        history.setPath(filename + ".jpg");
        historyRepository.save(history);
    }

    public Map<String, List<Object>> getHistory(String email, HistoryIntervalDto historyIntervalDto) throws ParseException, AppException {
        Calendar calendarFirstDay = Calendar.getInstance();
        Calendar calendarLastDay = Calendar.getInstance();

        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(historyIntervalDto.getStartingDate());
        calendarLastDay.setTime(endDate);

        Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(historyIntervalDto.getStartingDate());
        calendarFirstDay.setTime(startDate);

        if (calendarFirstDay.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY && calendarLastDay.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            throw new AppException("Bad Request!");
        }
        Map<String, List<Object>> weeklyHistoryMap = new HashMap<>();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);
        List<Object> weeklyProductHistoryList = new ArrayList<>();
        List<Object> weeklyNutrientsHistoryList = new ArrayList<>();
        int index = 0;
        while (index < 7) {
            String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(calendarFirstDay.getTime());
            Map<String, Object> foodList = getHistoryByDate(currentDate, appUser.getAppUserHistorySet());
            weeklyProductHistoryList.add(foodList.get("products"));
            weeklyNutrientsHistoryList.add(foodList.get("dailyNutrients"));
            index++;
            calendarFirstDay.add(Calendar.DATE, 1);
        }
        weeklyHistoryMap.put("products", weeklyProductHistoryList);
        weeklyHistoryMap.put("dailyNutrients", weeklyNutrientsHistoryList);
        return weeklyHistoryMap;
    }

    public Map<String, Object> getHistoryByDate(String currentDate, List<History> historyList) {
        Map<String, Object> historyListByDay = new HashMap<>();
        ArrayList<Object> foodListByDay = new ArrayList<>();
        DailyNutrientsDto dailyNutrientsDto = new DailyNutrientsDto();
        for (History history : historyList) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String historyDate = dateFormat.format(history.getCreatedAt());
            if (Objects.equals(currentDate, historyDate)) {
                FoodDto foodDto = new FoodDto(history.getFood().getName(), history.getQuantity(), history.getPath());
                FoodDto calculatedFoodDto = foodService.calculateNutritionalValues(history.getFood(), foodDto);
                foodListByDay.add(calculatedFoodDto);
                foodService.getDailyNutrients(dailyNutrientsDto, calculatedFoodDto);
            }
        }
        historyListByDay.put("products", foodListByDay);
        historyListByDay.put("dailyNutrients", dailyNutrientsDto);
        return historyListByDay;
    }


}
