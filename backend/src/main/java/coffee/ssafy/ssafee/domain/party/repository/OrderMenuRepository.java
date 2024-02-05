package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

    List<OrderMenu> findAllByPartyId(Long partyId);

    void deleteByPartyIdAndId(Long partyId, Long id);

}
