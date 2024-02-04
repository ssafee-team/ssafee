package coffee.ssafy.ssafee.domain.manager.repository;

import coffee.ssafy.ssafee.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, String> {

    Optional<Manager> findByIdAndPassword(String id, String password);

}
