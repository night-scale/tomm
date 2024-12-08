package sgms.ugc.repository;

import sgms.ugc.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike, Long> {

    @Modifying
    @Transactional  // 确保此操作在事务中执行
    @Query("DELETE FROM post_likes e WHERE e.userId = ?1 AND e.postId = ?2")
    int deleteLike(Long userId, Long postId);
}
