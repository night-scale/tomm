package sgms.ugc.repository;

import sgms.ugc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByTel(String identifier);

    User findByEmail(String identifier);
}
