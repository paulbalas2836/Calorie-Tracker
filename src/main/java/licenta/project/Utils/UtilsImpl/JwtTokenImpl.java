package licenta.project.Utils.UtilsImpl;

import io.jsonwebtoken.*;
import licenta.project.Exceptions.AppException;
import licenta.project.Models.AppUser;
import licenta.project.Utils.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
@Log4j2
public class JwtTokenImpl implements JwtToken {
    private final String secret_key = "secret";

    public String extractEmail(Claims claims) {
        return claims.get("email").toString();
    }

    public Date extractExpiration(String token) throws AppException {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractCreationDate(String token) throws AppException {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Long extractId(Claims claims) {
        return Long.parseLong(claims.get("id").toString());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws AppException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean isJwtNull(String token) throws AppException {
        if (token != null) {
            return true;
        } else {
            log.error("Jwt is null!");
            throw new AppException("Jwt is null!");
        }
    }

    public Claims extractAllClaims(String token) throws AppException {
        try {
            return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException
                | UnsupportedJwtException
                | MalformedJwtException
                | SignatureException
                | IllegalArgumentException e) {
            throw new AppException("Invalid Jwt");
        }
    }

    private Boolean isTokenExpired(String token) throws HttpServerErrorException, AppException {
        if (!extractExpiration(token).before(new Date())) {
            return false;
        } else {
            log.error("Token is expired!");
            throw new AppException("Token is expired!");
        }
    }

    public String createToken(Map<String, Object> claims) {
        long timeUntilExpiration = 1000 * 60 * 60 * 10; // 10hours
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + timeUntilExpiration))
                .signWith(SignatureAlgorithm.HS384, secret_key)
                .compact();
    }

    public Boolean validateEmail(String email, Claims claims) throws HttpServerErrorException, AppException {
        if (this.extractEmail(claims).equals(email)) {
            return true;
        } else {
            log.error("Invalid email!");
            throw new AppException("Invalid email!");
        }
    }

    public Boolean validateToken(String token, AppUser appUser) throws HttpServerErrorException, AppException {
        try {
            Claims claims =
                    Jwts.parser()
                            .setSigningKey(DatatypeConverter.parseBase64Binary(secret_key))
                            .parseClaimsJws(token)
                            .getBody();
            validateEmail(appUser.getEmail(), claims);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }
}
