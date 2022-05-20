package licenta.project.Services.ServiceImpl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import licenta.project.Dto.LoginDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.AppUser;
import licenta.project.Services.AppUserService;
import licenta.project.Services.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AppUserService appUserService;

    @Override
    public String loginAccount(LoginDto loginDto) throws AppException {
        boolean emailBoolean = appUserService.emailExists(loginDto.getEmail());
        boolean passwordBool = false;
        if (emailBoolean) {
            if (!appUserService.isProviderLocale(loginDto.getEmail()))
                throw new AppException("Email and password don't match!");
            passwordBool = BCrypt.checkpw(loginDto.getPassword(), appUserService.loadUserByUsername(loginDto.getEmail()).getPassword());
        }

        if (!emailBoolean || !passwordBool) {
            throw new AppException("Email and password don't match!");
        }

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(loginDto.getEmail());
        if (!appUser.getEnabled()) {
            throw new AppException("Account is not enable!");
        }

        return appUserService
                .getJwtUtil()
                .createToken(getClaims((AppUser) appUserService.loadUserByUsername(loginDto.getEmail())));
    }

    public GoogleIdToken verifyGoogleToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("1078733011731-q159frn9qatl2h8iiqt11jkhh8o68nmq.apps.googleusercontent.com"))
                .build();

        return verifier.verify(idTokenString);
    }

    @Override
    public String connectWithGoogle(String token) throws AppException, GeneralSecurityException, IOException {
        try {
            GoogleIdToken idToken = this.verifyGoogleToken(token);
            if (idToken == null) throw new AppException("Invalid token");

            Payload payload = idToken.getPayload();
            if (!appUserService.emailExists(payload.getEmail())) {
                appUserService.registerWithGoogle(payload);
            }
            AppUser appUser = (AppUser) appUserService.loadUserByUsername(payload.getEmail());
            Map<String, Object> map = getClaims(appUser);
            return appUserService.getJwtUtil().createToken(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> getClaims(AppUser appUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", appUser.getEmail());
        map.put("id", appUser.getId());
        return map;
    }
}
