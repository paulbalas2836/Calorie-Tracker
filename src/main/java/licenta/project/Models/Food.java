package licenta.project.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
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

}
