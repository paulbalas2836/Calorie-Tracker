package licenta.project.Controllers;

import licenta.project.Dto.JwtDto;
import licenta.project.Dto.LoginDto;
import licenta.project.Services.AppUserService;
import licenta.project.Services.LoginService;
import licenta.project.Utils.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/login")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class LoginController {
    private LoginService loginService;
    private JwtToken jwtToken;
    private AppUserService appUserService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) throws Exception {
        try {
            String jwt = this.loginService.loginAccount(loginDto);
            return new ResponseEntity(
                    new JwtDto(jwt, appUserService.getAppUserByClaims(this.jwtToken.extractAllClaims(jwt))),
                    HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

}
