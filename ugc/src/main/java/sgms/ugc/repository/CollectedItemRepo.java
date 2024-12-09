package sgms.ugc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgms.ugc.model.CollectedItem;

@Repository
public interface CollectedItemRepo extends JpaRepository<CollectedItem, Long> {
}
