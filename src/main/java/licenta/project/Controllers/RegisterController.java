package licenta.project.Controllers;

import licenta.project.Dto.RegisterDto;
import licenta.project.Exceptions.UserAlreadyExistAuthenticationException;
import licenta.project.Services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class RegisterController {
    private AppUserService appUserService;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegisterDto registerDto) throws UserAlreadyExistAuthenticationException {
        return new ResponseEntity(appUserService.register(registerDto), HttpStatus.CREATED);
    }

    @GetMapping(path ="confirm")
    public String confirm(@RequestParam("token") String token){
        return appUserService.confirmToken(token);
    }
}
