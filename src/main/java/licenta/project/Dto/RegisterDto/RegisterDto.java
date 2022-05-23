package licenta.project.Dto.RegisterDto;

import licenta.project.Annotations.RegisterPasswordMatches;
import licenta.project.Annotations.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@RegisterPasswordMatches(message = "The confirm password doesn't match the password")
@NoArgsConstructor
public class RegisterDto {
    @NotNull(message = "A name is required!")
    @NotEmpty(message = "The name should not be empty!")
    private String name;

    @NotEmpty(message = "The email address is required!")
    @NotNull(message = "The email address is required!")
    @ValidEmail(message = "The email address is not valid")
    private String email;

    @NotNull(message = "The password is required!")
    @NotEmpty(message = "The password must not be empty!")
    private String password;
    private String matchingPassword;

}
