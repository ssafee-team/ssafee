package coffee.ssafy.ssafee.domain.room.repository;

import coffee.ssafy.ssafee.domain.room.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
}
