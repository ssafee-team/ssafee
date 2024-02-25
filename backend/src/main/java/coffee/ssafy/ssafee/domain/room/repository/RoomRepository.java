package coffee.ssafy.ssafee.domain.room.repository;

import coffee.ssafy.ssafee.common.DateUtils;
import coffee.ssafy.ssafee.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {

    boolean existsByIdAndUserId(String id, Long userId);

    default List<Room> findAllByCreatedAtBetween(LocalDate startDate, LocalDate endDate) {
        return findAllByCreatedAtBetween(
                startDate.atStartOfDay().toInstant(DateUtils.ZONE_OFFSET),
                endDate.plusDays(1).atStartOfDay().toInstant(DateUtils.ZONE_OFFSET)
        );
    }

    List<Room> findAllByCreatedAtBetween(Instant startDateTime, Instant endDateTime);

}
