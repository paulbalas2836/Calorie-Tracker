package licenta.project.Services.ServiceImpl;

import licenta.project.Dto.DailyNutrientsDto;
import licenta.project.Dto.FoodDto;
import licenta.project.Dto.HistoryDateDto;
import licenta.project.Dto.SaveHistoryDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.AppUser;
import licenta.project.Models.Food;
import licenta.project.Models.History;
import licenta.project.Models.HistoryImage;
import licenta.project.Repositories.HistoryImageRepository;
import licenta.project.Repositories.HistoryRepository;
import licenta.project.Services.AppUserService;
import licenta.project.Services.FoodService;
import licenta.project.Services.HistoryService;
import licenta.project.Utils.UtilsImpl.ImageManipulationImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Getter
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final AppUserService appUserService;
    private final ImageManipulationImpl imageManipulationImpl;
    private final FoodService foodService;
    private final HistoryImageRepository historyImageRepository;

    @Override
    public FoodDto getDataFromPrediction(MultipartFile image, SaveHistoryDto saveHistoryDto) throws AppException {
        Optional<Food> defaultFood = foodService.getFood(saveHistoryDto.getLabel());
        if (defaultFood.isEmpty()) throw new AppException("No food found");
        FoodDto foodDto = new FoodDto(saveHistoryDto.getLabel(), saveHistoryDto.getWeight());
        if (foodDto.getQuantity() == null)
            foodDto.setQuantity(defaultFood.get().getDefaultQuantity());
        HistoryImage historyImage = new HistoryImage();
        addHistoryImage(historyImage);

        String path = "TrainImages/" + saveHistoryDto.getLabel();
        imageManipulationImpl.saveHistoryImage(image, saveHistoryDto.getLabel(), path, historyImage);

        addToHistory(saveHistoryDto.getEmail(), defaultFood.get(), foodDto.getQuantity(), historyImage);

        return foodService.calculateNutritionalValues(defaultFood.get(), foodDto);
    }

    public void addToHistory(String email, Food food, Double quantity, HistoryImage historyImage) {
        AppUser appUser;
        if (appUserService.emailExists(email)) {
            appUser = (AppUser) appUserService.loadUserByUsername(email);
        } else {
            appUser = null;
        }

        History history = new History(appUser, food, quantity, historyImage);
        historyRepository.save(history);
    }

    @Override
    public Map<String, Object> getHistory(String email, HistoryDateDto historyDateDto) throws ParseException {
        Calendar calendar = Calendar.getInstance();

        Date selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(historyDateDto.getSelectedDate());
        calendar.setTime(selectedDate);

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);

        return getHistoryByDate(calendar.getTime(), appUser.getAppUserHistorySet());
    }

    public Map<String, Object> getHistoryByDate(Date date, List<History> historyList) {
        Map<String, Object> historyListByDay = new HashMap<>();
        ArrayList<Object> foodListByDay = new ArrayList<>();
        DailyNutrientsDto dailyNutrientsDto = new DailyNutrientsDto();
        for (History history : historyList) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String historyDate = dateFormat.format(history.getCreatedAt());
            String currentDate = dateFormat.format(date);
            if (Objects.equals(currentDate, historyDate)) {
                FoodDto foodDto = new FoodDto(history.getFood().getName(), history.getQuantity(), history.getHistoryImage().getPath());
                FoodDto calculatedFoodDto = foodService.calculateNutritionalValues(history.getFood(), foodDto);
                foodListByDay.add(calculatedFoodDto);
                foodService.getDailyNutrients(dailyNutrientsDto, calculatedFoodDto);
            }
        }
        historyListByDay.put("products", foodListByDay);
        historyListByDay.put("dailyNutrients", dailyNutrientsDto);
        return historyListByDay;
    }

    public void addHistoryImage(HistoryImage historyImage) {
        historyImageRepository.save(historyImage);
    }

}
