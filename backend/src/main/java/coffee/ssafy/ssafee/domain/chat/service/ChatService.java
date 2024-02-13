package coffee.ssafy.ssafee.domain.chat.service;

import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import coffee.ssafy.ssafee.domain.chat.mapper.ChatMapper;
import coffee.ssafy.ssafee.domain.chat.repository.ChatRepository;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Transactional
    public List<ChatResponse> findChats(Long partyId) {
        return chatRepository.findAllByPartyId(partyId).stream()
                .map(chatMapper::toDto)
                .toList();
    }

    @Transactional
    public void createChat(Long partyId, ChatRequest chatRequest) {
        Party partyReference = entityManager.getReference(Party.class, partyId);
        Chat chat = Chat.builder()
                .name("")
                .content(chatRequest.content())
                .party(partyReference)
                .build();
        chatRepository.save(chat);
    }

}
