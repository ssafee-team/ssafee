package coffee.ssafy.ssafee.domain.party.repository;

import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<Party, Long> {
}
