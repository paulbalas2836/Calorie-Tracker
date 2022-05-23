package licenta.project.Dto.HistoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodHistoryDto {


    private String name;
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
    private Double quantity;
    private String path;

    public FoodHistoryDto(String name, Double quantity){
        this.path = null;
        this.name = name;
        this.quantity = quantity;
    }
    public FoodHistoryDto(String name, Double quantity, String path){
        this.path = path;
        this.name = name;
        this.quantity = quantity;
    }
}
