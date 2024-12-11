package sgms.ugc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgms.ugc.model.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

}
