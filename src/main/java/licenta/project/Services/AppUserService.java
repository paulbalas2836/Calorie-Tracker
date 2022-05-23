package licenta.project.Services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.jsonwebtoken.Claims;
import licenta.project.Dto.AppUserDto.ChangePasswordDto;
import licenta.project.Dto.LoginDto.AppUserDto;
import licenta.project.Dto.RegisterDto.RegisterDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Exceptions.UserAlreadyExistAuthenticationException;
import licenta.project.Models.AppUser;
import licenta.project.Utils.JwtToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

public interface AppUserService {
    void register(RegisterDto registerDto) throws UserAlreadyExistAuthenticationException, MessagingException;

    void registerWithGoogle(GoogleIdToken.Payload googleAppUser);

    void confirmToken(String token);

    JwtToken getJwtUtil();

    Boolean emailExists(String email);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    AppUser findAppUserById(Long id) throws AppException;

    Boolean isProviderLocale(String email);

    AppUserDto getAppUserByClaims(Claims claims) throws AppException;

    void changePassword(String email, ChangePasswordDto changePasswordDto) throws AppException;

    String changeProfileImage(String email, MultipartFile image) throws IOException;

    void disableAppUser(String email) throws AppException;
}
