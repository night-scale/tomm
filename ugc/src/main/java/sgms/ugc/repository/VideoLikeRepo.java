package sgms.ugc.repository;

import sgms.ugc.model.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VideoLikeRepo extends JpaRepository<VideoLike, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM VideoLike e WHERE e.userId = ?1 AND e.videoId = ?2")
    int deleteLike(Long userId, Long videoId);
}
