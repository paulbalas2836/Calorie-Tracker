package licenta.project.Controllers;

import licenta.project.Dto.ChangePasswordDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class AppUserController {
    private final AppUserService appUserService;

    @PutMapping(path = "/changePassword/{userEmail}")
    public ResponseEntity<?> changePassword(@PathVariable String userEmail, @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            appUserService.changePassword(userEmail, changePasswordDto);
        } catch (AppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/changeProfileImage/{userEmail}")
    public ResponseEntity<String> changeProfileImage(@PathVariable String userEmail, @RequestPart("image") @Valid @NotNull @NotEmpty MultipartFile image) {
        try {
            return new ResponseEntity<>(appUserService.changeProfileImage(userEmail, image), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/disableAppUser/{userEmail}")
    public ResponseEntity<?> disableAppUser(@PathVariable String userEmail) {
        try {
            appUserService.disableAppUser(userEmail);
        } catch (AppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
