package coffee.ssafy.ssafee.domain.room.repository;

import coffee.ssafy.ssafee.domain.room.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findAllByRoomId(String roomId);

    List<Participant> findAllByRoomIdAndPickedCarrierIsFalse(String roomId);

    List<Participant> findAllByRoomIdAndPaidIsFalse(String roomId);

    Optional<Participant> findByRoomIdAndId(String roomId, Long id);

    Optional<Participant> findByRoomIdAndName(String roomId, String name);

}
