package licenta.project.Repositories;

import licenta.project.Models.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface HistoryRepository extends JpaRepository<History, Long> {
}
