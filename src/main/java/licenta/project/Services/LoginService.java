package licenta.project.Services;

import licenta.project.Dto.LoginDto.LoginDto;
import licenta.project.Exceptions.AppException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface LoginService {
    String loginAccount(LoginDto loginDto) throws AppException;

    String connectWithGoogle(String token) throws AppException, GeneralSecurityException, IOException;
}
