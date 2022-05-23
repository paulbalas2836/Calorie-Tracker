package licenta.project.Dto.HistoryDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyNutrientsDto {

    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double fiber;
    private Double calcium;
    private Double cholesterol;
    private Double potassium;
    private Double sodium;
    private Double iron;

    public DailyNutrientsDto() {
        this.calcium = 0.0;
        this.calories = 0.0;
        this.cholesterol = 0.0;
        this.protein = 0.0;
        this.fat = 0.0;
        this.carbs = 0.0;
        this.fiber = 0.0;
        this.potassium = 0.0;
        this.sodium = 0.0;
        this.iron = 0.0;
    }
}
