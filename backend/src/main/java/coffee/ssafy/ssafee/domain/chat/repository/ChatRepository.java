package coffee.ssafy.ssafee.domain.chat.repository;

import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByPartyId(Long partyId);

}
