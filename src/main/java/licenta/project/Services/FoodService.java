package licenta.project.Services;

import licenta.project.Dto.FoodDto;
import licenta.project.Models.Food;
import licenta.project.Models.History;
import licenta.project.Repositories.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

//    public List<Food> getFoodByHistory(List<History> historySet) throws ParseException {
//        Date date = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);


//        int endDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//        int startDayOfMonth = 0;
//        ArrayList<Boolean> weekList = new ArrayList<>();
//        weekList.add(0, cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);
//        weekList.add(1, cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY);
//        weekList.add(2, cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY);
//        weekList.add(3, cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY);
//        weekList.add(4, cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY);
//        weekList.add(5, cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
//        weekList.add(6, cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
//
//        for (int i = 0; i < weekList.size(); i++) {
//            if (weekList.get(i)) {
//                startDayOfMonth = endDayOfMonth - i;
//            }
//        }
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        ArrayList<List<History>> historyListByDay = new ArrayList<>();
//        while (startDayOfMonth <= endDayOfMonth) {
//            Calendar days = Calendar.getInstance();
//            List<History> historyList = new ArrayList<>();
//            for (History history : historySet) {
//                if ((formatter.format(history.getCreatedAt())).equals(formatter.format(cal.getTime()))) {
//                    historyList.add(history);
//                }
//            }
//            historyListByDay.add(historyList);
//            //Adauga in list o lista cu obiectele history care apartin acelei zi
//            days.add(Calendar.DATE, -1);
//            startDayOfMonth++;
//        }

//        return null;
//    }

}
