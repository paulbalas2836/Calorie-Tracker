package licenta.project.Services;

import licenta.project.Models.AppUser;
import licenta.project.Models.Food;
import licenta.project.Repositories.FoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.SavedModelBundle;

@Service
public class FoodService {
    private FoodRepository foodRepository;
    private AppUserService appUserService;

    public Food prediction(MultipartFile image, Double weight, String email){
       if(email != null)
       {


       }
        return new Food();
    }

}
