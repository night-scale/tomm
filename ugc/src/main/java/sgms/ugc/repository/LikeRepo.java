package sgms.ugc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sgms.ugc.model.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

    @Modifying
    @Transactional
    int deleteLikeByContent_IdAndCreator_Id(Long content, Long creator);
}
