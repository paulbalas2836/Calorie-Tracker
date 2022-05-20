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

    @PutMapping(path = "/changeProfileImage/{userEmail}")
    public ResponseEntity<?> changeProfileImage(@PathVariable String userEmail, @Valid @NotEmpty @NotNull @RequestPart("image") MultipartFile image){
        try{
            appUserService.changeProfileImage(userEmail, image);
        }catch (IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
