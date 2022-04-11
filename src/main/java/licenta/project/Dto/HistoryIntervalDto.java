package licenta.project.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class HistoryIntervalDto {
    @NotNull
    @NotEmpty
    private String startingDate;

    @NotEmpty
    @NotNull
    private String endingDate;
}
