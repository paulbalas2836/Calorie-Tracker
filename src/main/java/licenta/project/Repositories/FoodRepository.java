package licenta.project.Repositories;

import licenta.project.Models.Food;
import licenta.project.Records.FoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> getFoodByName(String name);
    List<FoodRecord> findAllProjectionsBy();
}
