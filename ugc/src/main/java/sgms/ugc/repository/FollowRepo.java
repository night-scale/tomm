package sgms.ugc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgms.ugc.model.Follow;

import java.util.Optional;

@Repository
public interface FollowRepo extends JpaRepository<Follow, Long> {

    int deleteByFromUser_IdAndToUser_Id(Long fromUserId, Long toUserId);
}
