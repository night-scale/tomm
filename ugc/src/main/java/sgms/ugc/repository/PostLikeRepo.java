package sgms.ugc.repository;

import sgms.ugc.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PostLike e WHERE e.userId = ?1 AND e.postId = ?2")
    int deleteLike(Long col1, Long col2);
}
