package licenta.project.Services;

import licenta.project.Dto.RegisterDto;
import licenta.project.Exceptions.UserAlreadyExistAuthenticationException;
import licenta.project.Models.AppUser;
import licenta.project.Models.ConfirmationToken;
import licenta.project.Repositories.AppUserRepository;
import licenta.project.Utils.Mails.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService {
    private AppUserRepository appUserRepository;
    private ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

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

    private Boolean emailExists(String email) {
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
}
