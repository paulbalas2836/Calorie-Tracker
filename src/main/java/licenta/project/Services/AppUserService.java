package licenta.project.Services;

import licenta.project.Repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {
    private AppUserRepository appUserRepository;

}
