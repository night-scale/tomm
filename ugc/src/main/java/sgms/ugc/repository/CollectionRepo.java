package sgms.ugc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sgms.ugc.model.Collection;

@Repository
public interface CollectionRepo extends JpaRepository<Collection, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Collection c WHERE c.id = :id AND c.creator.id = :creator")
    int deleteByIdAndCreator(@Param("id") Long id, @Param("creator") Long creatorId);
}
