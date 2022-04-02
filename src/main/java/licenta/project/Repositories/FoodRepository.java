package licenta.project.Repositories;

import licenta.project.Models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FoodRepository extends JpaRepository<Food, Long> {
}
