package sgms.ugc.repository;

import sgms.ugc.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByIdIn(List<Long> ids);
}
