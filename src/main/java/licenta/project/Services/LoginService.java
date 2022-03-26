package licenta.project.Services;

import licenta.project.Dto.LoginDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private AppUserService appUserService;

    public String loginAccount(LoginDto loginDto) throws AppException {
        boolean emailBoolean = this.appUserService.emailExists(loginDto.getEmail());
        boolean passwordBool = false;
        if (emailBoolean) {
            passwordBool = BCrypt.checkpw(loginDto.getPassword(), this.appUserService.loadUserByUsername(loginDto.getEmail()).getPassword());
        }

        if (!emailBoolean || !passwordBool) {
            throw new AppException("Password or email incorrect!");
        }

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(loginDto.getEmail());
        if(!appUser.getEnabled())
        {
            throw new AppException("Account is not enable!");
        }

        return this.appUserService
                .getJwtUtil()
                .createToken(
                        this.getClaims((AppUser) this.appUserService.loadUserByUsername(loginDto.getEmail())));
    }
    public Map<String, Object> getClaims(AppUser appUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", appUser.getEmail());
        map.put("id", appUser.getId());
        return map;
    }
}
