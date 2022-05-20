package licenta.project.Models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne(targetEntity = Food.class)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "histroy_image_id", referencedColumnName = "id")
    private HistoryImage historyImage;

    public History(AppUser appUser, Food food, Double quantity, HistoryImage historyImage) {
        this.appUser = appUser;
        this.food = food;
        this.quantity = quantity;
        this.historyImage = historyImage;
    }
}
