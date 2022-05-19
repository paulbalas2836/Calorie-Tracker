package licenta.project.Controllers;

import licenta.project.Dto.ChangePasswordDto;
import licenta.project.Exceptions.AppException;
import licenta.project.Services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @PutMapping(path = "/changePassword/{userEmail}")
    public ResponseEntity<?> changePassword(@PathVariable String userEmail, @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            appUserService.changePassword(userEmail, changePasswordDto);
        } catch (AppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PutMapping(path = "/changeProfileImage/{userEmail}")
//    public ResponseEntity<?> changeProfileImage(@PathVariable String userEmail, @Valid @NotEmpty @NotNull @RequestPart("image") MultipartFile image){
//        try{
//            appUserService.changeProfileImage(userEmail, image);
//        }catch (AppException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
