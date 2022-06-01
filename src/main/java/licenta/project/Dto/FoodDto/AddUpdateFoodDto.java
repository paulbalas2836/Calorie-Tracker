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
    @NotNull(message = "Food name should not be null!")
    @NotEmpty(message = "Food name should not be empty!")
    private String name;

    @NotNull(message = "Food calories should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for calories!")
    private Double calories;

    @NotNull(message = "Food protein should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for proteins!")
    private Double protein;

    @NotNull(message = "Food fat should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for fat!")
    private Double fat;

    @NotNull(message = "Food carbs should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for carbs!")
    private Double carbs;

    @NotNull(message = "Food fiber should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for fiber!")
    private Double fiber;

    @NotNull(message = "Food calcium should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for calcium!")
    private Double calcium;

    @NotNull(message = "Food cholesterol should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for cholesterol!")
    private Double cholesterol;

    @NotNull(message = "Food potassium should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for potassium!")
    private Double potassium;

    @NotNull(message = "Food sodium should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for sodium!")
    private Double sodium;

    @NotNull(message = "Food iron should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for iron!")
    private Double iron;

    @NotNull(message = "Food quantity should not be null!")
    @Digits(integer = 6, fraction = 2, message = "Wrong format for quantity!")
    private Double defaultQuantity;
}
