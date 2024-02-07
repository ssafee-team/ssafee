package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceMenuRepository extends JpaRepository<ChoiceMenu, Long> {

    List<ChoiceMenu> findAllByPartyId(Long partyId);

    void deleteByPartyIdAndId(Long partyId, Long id);

}
