package licenta.project.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDto {
    String jwt;
    AppUserDto appUserDto;
}
