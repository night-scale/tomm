package sgms.ugc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgms.ugc.model.Collection;

@Repository
public interface CollectionRepo extends JpaRepository<Collection, Long> {
}
