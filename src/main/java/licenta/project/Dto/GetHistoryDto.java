package licenta.project.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class GetHistoryDto {
    @NotNull
    @NotEmpty
    private String email;
}
