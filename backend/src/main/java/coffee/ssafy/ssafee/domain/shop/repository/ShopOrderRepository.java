package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Collection;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<Party, Long> {

    List<Party> findAllByShopIdAndDeliveredTimeIsNotNull(@Param("shopId") Long shopId);

    List<Party> findAllById(Long partyId);
}
