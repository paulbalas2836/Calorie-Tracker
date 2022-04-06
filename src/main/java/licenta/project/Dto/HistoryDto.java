package licenta.project.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class HistoryDto {

    private Double weight;
    @NotNull
    private String label;
    private String email;

}
