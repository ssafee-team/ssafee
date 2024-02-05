package coffee.ssafy.ssafee.domain.chat.service;

import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import coffee.ssafy.ssafee.domain.chat.mapper.ChatMapper;
import coffee.ssafy.ssafee.domain.chat.repository.ChatRepository;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
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
    private final PartyService partyService;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Transactional
    public void createChat(String accessCode, ChatRequest chatRequest) {
        Long partyId = partyService.findPartyIdByAccessCode(accessCode);
        Chat chat = Chat.builder()
                .content(chatRequest.content())
                .party(entityManager.getReference(Party.class, partyId))
                .build();
        chatRepository.save(chat);
    }

    @Transactional
    public List<ChatResponse> getChats(String accessCode) {
        Long partyId = partyService.findPartyIdByAccessCode(accessCode);
        return chatRepository.findAllByPartyId(partyId).stream()
                .map(chatMapper::toDto)
                .toList();
    }

}
