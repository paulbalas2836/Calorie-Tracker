package licenta.project.Services;

import io.jsonwebtoken.Claims;
import licenta.project.Dto.AppUserDto;
import licenta.project.Dto.RegisterDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Exceptions.UserAlreadyExistAuthenticationException;
import licenta.project.Models.AppUser;
import licenta.project.Models.ConfirmationToken;
import licenta.project.Repositories.AppUserRepository;
import licenta.project.Repositories.Interfaces.EmailSender;
import licenta.project.Utils.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private AppUserRepository appUserRepository;
    private ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final JwtToken jwtToken;
    private final static String USER_NOT_FOUND = "user with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

    public void register(RegisterDto registerDto) throws UserAlreadyExistAuthenticationException, MessagingException {
        if (emailExists(registerDto.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("The email address is already taken!");
        }

        String encodedPassword = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt(12));

        AppUser appUser = new AppUser();
        appUser.setEmail(registerDto.getEmail());
        appUser.setName(registerDto.getName());
        appUser.setEnabled(false);
        appUser.setPassword(encodedPassword);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(appUser, token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String link = "http://localhost:8080/register/confirm?token=" + token;
        emailSender.send(link, registerDto.getEmail());
    }

    public Boolean emailExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }


    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
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

    public AppUser findAppUserById(Long id){
        return appUserRepository.findById(id).orElseThrow(()-> new AppException(HttpStatus.BAD_REQUEST, "User "+ id + " was not found"));
    }

    public AppUserDto getAppUserById(Long userId){
        AppUser appUser;
        appUser = findAppUserById(userId);

        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setImage(appUser.getProfileImage());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setName(appUser.getName());
        return appUserDto;
    }
    public AppUserDto getAppUserByClaims(Claims claims){
        Long userId = jwtToken.extractId(claims);
        return getAppUserById(userId);
    }

}
