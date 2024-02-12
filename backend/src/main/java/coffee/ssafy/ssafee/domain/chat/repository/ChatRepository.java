package coffee.ssafy.ssafee.domain.chat.repository;

import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    // party_id에 해당되는 ChatId 찾기 = 해당 파티에 채팅방이 존재
    Optional<Chat> findById(Long partyId);
    List<Chat> findAllByPartyId(Long partyId);

}
