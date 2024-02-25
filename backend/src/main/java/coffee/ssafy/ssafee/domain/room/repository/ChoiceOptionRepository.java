package coffee.ssafy.ssafee.domain.room.repository;

import coffee.ssafy.ssafee.domain.room.entity.ChoiceOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceOptionRepository extends JpaRepository<ChoiceOption, Long> {
    List<ChoiceOption> findAllByRoomId(String roomId);

}
