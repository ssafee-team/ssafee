package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByAccessCode(String accessCode);

    @Query("select om from Party p join p.orderMenus om where p.accessCode = :accessCode and om.id = :id")
    Optional<OrderMenu> findOrderMenuByAccessCodeAndId(String accessCode, Long id);

}
