package coffee.ssafy.ssafee.domain.user.repository;

import coffee.ssafy.ssafee.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

}
