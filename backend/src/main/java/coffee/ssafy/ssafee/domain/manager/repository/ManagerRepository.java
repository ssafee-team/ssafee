package coffee.ssafy.ssafee.domain.manager.repository;

import coffee.ssafy.ssafee.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, String> {

    boolean existsByIdAndPassword(String id, String password);

}
