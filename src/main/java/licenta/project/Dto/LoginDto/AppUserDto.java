package licenta.project.Dto.LoginDto;

import licenta.project.Struct.Provider;
import licenta.project.Struct.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDto {
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private Provider provider;

    @NotEmpty
    @NotNull
    private String image;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private Roles role;
}
