package coffee.ssafy.ssafee.domain.room.repository;

import coffee.ssafy.ssafee.domain.room.entity.ChoiceMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChoiceMenuRepository extends JpaRepository<ChoiceMenu, Long> {

    List<ChoiceMenu> findAllByRoomId(String roomId);

    List<ChoiceMenu> findAllByParticipantId(Long participantId);

    Optional<ChoiceMenu> findByRoomIdAndId(String roomId, Long id);

    boolean existsByParticipantId(Long participantId);

}
