package sgms.ugc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sgms.ugc.model.Comment;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :commentId AND c.user.id = :userId")
    int deleteByIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

    Page<Comment> findByContent(Long content, Pageable pageable);

    List<Comment> findByContentAndParentIsNull(Long content, Sort sort);

    Page<Comment> findByContentAndParentIsNull(Long content, Pageable pageable);

    Page<Comment> findByParent(Long parent, Pageable pageable);
}
