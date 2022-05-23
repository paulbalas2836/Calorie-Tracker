package licenta.project.Dto.AppUserDto;

import com.sun.istack.NotNull;
import licenta.project.Annotations.ChangePasswordMatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ChangePasswordMatches
public class ChangePasswordDto {

    @NotEmpty
    @NotNull
    private String currentPassword;

    @NotNull
    @NotEmpty
    private String newPassword;
    private String confirmNewPassword;

}
