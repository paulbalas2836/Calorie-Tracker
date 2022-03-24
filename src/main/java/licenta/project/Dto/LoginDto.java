package licenta.project.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginDto {
    @NotEmpty(message = "Email address should not be empty")
    @NotNull(message = "Email address is required")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
