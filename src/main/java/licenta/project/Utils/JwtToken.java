package licenta.project.Utils;

import io.jsonwebtoken.Claims;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.AppUser;

import java.util.Map;

public interface JwtToken {
    Long extractId(Claims claims);

    Claims extractAllClaims(String token) throws AppException;

    String createToken(Map<String, Object> claims);

    Boolean validateToken(String token, AppUser appUser) throws AppException;

    Boolean validateEmail(String email, Claims claims) throws AppException;
}
