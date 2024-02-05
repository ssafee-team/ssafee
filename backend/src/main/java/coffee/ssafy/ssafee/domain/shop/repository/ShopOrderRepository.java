package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.party.entity.Order;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<Order, Long> {
}
