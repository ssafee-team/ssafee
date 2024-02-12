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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyService partyService;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

//    @Transactional
//    public void createChat(Long partyId, ChatRequest chatRequest) {
//        Chat chat = Chat.builder()
//                .content(chatRequest.content())
//                .userName("")  // name
//                .party(entityManager.getReference(Party.class, partyId))
//                .build();
//        chatRepository.save(chat);
//    }
//
//    @Transactional
//    public List<ChatResponse> getChats(Long partyId) {
//        return chatRepository.findAllByPartyId(partyId).stream()
//                .map(chatMapper::toDto)
//                .toList();
//    }

    @Transactional
    public void createChat(Long partyId, ChatRequest chatRequest) {
        Chat chat = Chat.builder()
                .content(chatRequest.content())
                .userName("")
                .party(entityManager.getReference(Party.class, partyId))
                .build();
        chatRepository.save(chat);
    }

    @Transactional
    public Long findChatId(Long partyId) {
        return chatRepository.findById(partyId)
                .orElseThrow().getId();
    }

    @Transactional
    public void saveChat(String accessCode, ChatRequest chatRequest) {
        System.out.println("ChatService -> " + accessCode);

        // partyService에 이미 구현되어 있는 메소드: Long partyId를 반환하거나 Exception 발생.
        Long partyId = partyService.findPartyId(accessCode);  // party_id 찾기

        Party partyReference = entityManager.getReference(Party.class, partyId);

        Chat chat;
//        Optional<Chat> chatRoom = chatRepository.findById(partyId);
//        // 1. 이미 채팅방이 있다면
//        if (chatRoom.isPresent()) {
////            Chat chat = chatRoom.get();
//            chat = chatRoom.get();
//            chat.updateChat(chatRequest.userName(), chatRequest.content(), chatRequest.contentTime());
//        } else {  // 2. 해당 파티에 해당하는 채팅방이 없다면
//            chat = chatMapper.toEntity(chatRequest);
//            chat.prepareCreation(partyReference, chatRequest.userName(), chatRequest.content(), chatRequest.contentTime());
//        }

        chat = chatMapper.toEntity(chatRequest);
        chat.prepareCreation(partyReference, chatRequest.userName(), chatRequest.content(), chatRequest.contentTime());
        chatRepository.save(chat);
    }

}
