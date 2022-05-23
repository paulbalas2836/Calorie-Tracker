package licenta.project.Dto.FoodDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateFoodDto {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double calories;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double protein;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double fat;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double carbs;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double fiber;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double calcium;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double cholesterol;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double potassium;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double sodium;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double iron;
    @NotNull
    @Digits(integer=6, fraction=2)
    private Double quantity;
}
