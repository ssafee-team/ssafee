package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChoiceMenuRepository extends JpaRepository<ChoiceMenu, Long> {

    List<ChoiceMenu> findAllByPartyId(Long partyId);

    Optional<ChoiceMenu> findAllByIdAndPartyId(Long id, Long partyId);

    void deleteByPartyIdAndId(Long partyId, Long id);

}
