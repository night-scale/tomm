package sgms.ugc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgms.ugc.model.Content;

import java.util.List;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {

    List<Content> findAllByIdIn(List<Long> ids);
}
