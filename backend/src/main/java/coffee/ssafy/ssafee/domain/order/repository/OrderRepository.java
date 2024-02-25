package coffee.ssafy.ssafee.domain.order.repository;

import coffee.ssafy.ssafee.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByRoomId(String roomId);

    boolean existsByRoomId(String roomId);

    List<Order> findAllByShopId(Long shopId);

}
