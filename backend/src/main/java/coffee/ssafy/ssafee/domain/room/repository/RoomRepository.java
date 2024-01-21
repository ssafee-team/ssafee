package coffee.ssafy.ssafee.domain.room.repository;

import coffee.ssafy.ssafee.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
