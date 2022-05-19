package licenta.project.Services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.jsonwebtoken.Claims;
import licenta.project.Dto.AppUserDto;
import licenta.project.Dto.ChangePasswordDto;
import licenta.project.Dto.RegisterDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Exceptions.UserAlreadyExistAuthenticationException;
import licenta.project.Models.AppUser;
import licenta.project.Models.ConfirmationToken;
import licenta.project.Repositories.AppUserRepository;
import licenta.project.Repositories.Interfaces.EmailSender;
import licenta.project.Struct.Provider;
import licenta.project.Utils.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final JwtToken jwtToken;
    private final static String USER_NOT_FOUND = "user with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public void register(RegisterDto registerDto) throws UserAlreadyExistAuthenticationException, MessagingException {
        if (emailExists(registerDto.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("The email address is already taken!");
        }

        String encodedPassword = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt(10));

        AppUser appUser = new AppUser();
        appUser.setEmail(registerDto.getEmail());
        appUser.setName(registerDto.getName());
        appUser.setEnabled(false);
        appUser.setPassword(encodedPassword);
        appUser.setProfileImage("./profile_images/default_photo.png");
        appUser.setProvider(Provider.LOCAL);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(appUser, token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String link = "http://localhost:8080/register/confirm?token=" + token;
        emailSender.send(link, registerDto.getEmail());
    }

    public void registerWithGoogle(GoogleIdToken.Payload googleAppUser) {
        AppUser appUser = new AppUser();
        appUser.setName((String) googleAppUser.get("name"));
        appUser.setEmail(googleAppUser.getEmail());
        appUser.setEnabled(true);
        appUser.setProfileImage((String) googleAppUser.get("picture"));
        appUser.setProvider(Provider.GOOGLE);
        appUserRepository.save(appUser);
    }

    public Boolean emailExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }


    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }

    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableAppUser(confirmationToken.getAppUser().getEmail());
    }

    public JwtToken getJwtUtil() {
        return this.jwtToken;
    }

    public AppUser findAppUserById(Long id) throws AppException {
        return appUserRepository.findById(id).orElseThrow(() -> new AppException("User " + id + " was not found"));
    }

    public AppUserDto getAppUserById(Long userId) throws AppException {
        AppUser appUser;
        appUser = findAppUserById(userId);

        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setImage(appUser.getProfileImage());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setName(appUser.getName());
        appUserDto.setProvider(appUser.getProvider());
        return appUserDto;
    }

    public Boolean isProviderLocale(String email){
        AppUser appUser =(AppUser) loadUserByUsername(email);
        return appUser.getProvider() == Provider.LOCAL;
    }

    public AppUserDto getAppUserByClaims(Claims claims) throws AppException {
        Long userId = jwtToken.extractId(claims);
        return getAppUserById(userId);
    }

    public void changePassword(String email, ChangePasswordDto changePasswordDto) throws AppException {
        AppUser appUser = (AppUser) loadUserByUsername(email);
        if(!BCrypt.checkpw(changePasswordDto.getCurrentPassword(), appUser.getPassword()))
        {
            throw new AppException("Current password is incorrect!");
        }

        if(Objects.equals(changePasswordDto.getCurrentPassword(), changePasswordDto.getNewPassword()))
        {
            throw new AppException("Your new password should be different from the current one!");
        }

        appUser.setPassword(BCrypt.hashpw(changePasswordDto.getNewPassword(), BCrypt.gensalt(10)));
        appUserRepository.save(appUser);
    }

    public void changeProfileImage(String email, MultipartFile image){
        
    }

}
