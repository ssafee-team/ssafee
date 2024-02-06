package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.entity.Order;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopOrderRepository extends JpaRepository<Party, Long> {
    @Query("SELECT p FROM Party p JOIN p.order o WHERE p.shop.id = :shopId AND o.delivered = true")
    List<Party> findAllByShopIdAndOrderDelivered(@Param("shopId") Long shopId);
}
