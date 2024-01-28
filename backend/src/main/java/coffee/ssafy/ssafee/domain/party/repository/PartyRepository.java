package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByAccessCode(String accessCode);

    Optional<Long> findIdByAccessCode(String accessCode);

    default List<Party> findAllByCreatedTimeToday() {
        LocalDate today = LocalDate.now();
        return findAllByCreatedTimeBetween(today.atStartOfDay(), today.plusDays(1).atStartOfDay());
    }

    List<Party> findAllByCreatedTimeBetween(LocalDateTime start, LocalDateTime end);

}
