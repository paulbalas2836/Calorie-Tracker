package licenta.project.Records;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record FoodRecord(@NotNull Long id,
                         @NotNull @NotEmpty String name,
                         @NotNull @Digits(integer = 6, fraction = 2) Double calories,
                         @NotNull @Digits(integer = 6, fraction = 2) Double protein,
                         @NotNull @Digits(integer = 6, fraction = 2) Double fat,
                         @NotNull @Digits(integer = 6, fraction = 2) Double carbs,
                         @NotNull @Digits(integer = 6, fraction = 2) Double fiber,
                         @NotNull @Digits(integer = 6, fraction = 2) Double calcium,
                         @NotNull @Digits(integer = 6, fraction = 2) Double cholesterol,
                         @NotNull @Digits(integer = 6, fraction = 2) Double potassium,
                         @NotNull @Digits(integer = 6, fraction = 2) Double sodium,
                         @NotNull @Digits(integer = 6, fraction = 2) Double iron,
                         @NotNull @Digits(integer = 6, fraction = 2) Double defaultQuantity) {
}
