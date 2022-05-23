package licenta.project.Dto.HistoryDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class HistoryDateDto {
    @NotNull
    @NotEmpty
    private String selectedDate;
    
}
