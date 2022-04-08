package licenta.project.Services;

import licenta.project.Models.AppUser;
import licenta.project.Models.Food;
import licenta.project.Models.History;
import licenta.project.Repositories.HistoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
@AllArgsConstructor
public class HistoryService {
    private HistoryRepository historyRepository;
    private AppUserService appUserService;
    public void addToHistory(String email, Food food, String filename, Double quantity)
    {
        History history = new History();
        history.setAppUser((AppUser) appUserService.loadUserByUsername(email));
        history.setFood(food);
        history.setQuantity(quantity);
        history.setPath("D:\\Licenta\\Licenta\\TrainImages\\" + filename + ".jpg");
        historyRepository.save(history);
    }
}
