package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findAllByPartyId(Long partyId);

    Optional<Participant> findByPartyIdAndId(Long partyId, Long id);

    Optional<Participant> findByPartyIdAndName(Long partyId, String name);

    boolean existsByPartyIdAndIsCarrierIsTrue(Long partyId);

}
