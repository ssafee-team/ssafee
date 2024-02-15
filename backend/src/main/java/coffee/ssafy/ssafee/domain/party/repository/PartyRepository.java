package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByAccessCode(String accessCode);

    default List<Party> findAllByCreatedTimeBetween(LocalDate startDate, LocalDate endDate) {
        return findAllByCreatedTimeBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
    }

    List<Party> findAllByCreatedTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    boolean existsByAccessCodeAndUserId(String accessCode, Long userId);

    List<Party> findAllByShopIdAndRealOrderedTimeIsNotNull(@Param("shopId") Long shopId);

    Optional<Party> findByIdAndShopId(Long partyId, Long shopId);

}
