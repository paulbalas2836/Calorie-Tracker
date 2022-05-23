package licenta.project.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double calories;
    @Column(nullable = false)
    private Double protein;
    @Column(nullable = false)
    private Double fat;
    @Column(nullable = false)
    private Double carbs;
    @Column(nullable = false)
    private Double fiber;
    @Column(nullable = false)
    private Double calcium;
    @Column(nullable = false)
    private Double cholesterol;
    @Column(nullable = false)
    private Double potassium;
    @Column(nullable = false)
    private Double sodium;
    @Column(nullable = false)
    private Double iron;
    @Column(nullable = false)
    private Double defaultQuantity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "food")
    private List<History> foodHistorySet = new ArrayList<>();

    public Food(String name, Double calories, Double protein, Double fat, Double carbs, Double fiber,
                Double calcium, Double cholesterol, Double potassium, Double sodium, Double iron, Double defaultQuantity) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.calcium = calcium;
        this.cholesterol = cholesterol;
        this.potassium = potassium;
        this.sodium = sodium;
        this.iron = iron;
        this.defaultQuantity = defaultQuantity;
    }
}
