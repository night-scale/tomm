package sgms.ugc.repository;

import sgms.ugc.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepo extends JpaRepository<Video, Long> {
    List<Video> findAllByIdIn(List<Long> ids);
}
