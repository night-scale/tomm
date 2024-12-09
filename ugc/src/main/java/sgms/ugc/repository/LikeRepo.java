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
    @Transactional  // 确保此操作在事务中执行
    @Query("DELETE FROM Like e WHERE e.userId = ?1 AND e.contentId = ?2")
    int deleteLike(Long userId, Long contentId);
}
