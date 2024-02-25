package coffee.ssafy.ssafee.domain.chat.service;

import coffee.ssafy.ssafee.domain.chat.dto.ChatInfo;
import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import coffee.ssafy.ssafee.domain.chat.mapper.ChatMapper;
import coffee.ssafy.ssafee.domain.chat.repository.ChatRepository;
import coffee.ssafy.ssafee.domain.room.entity.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public ChatResponse create(String roomId, ChatRequest chatRequest) {
        var chat = Chat.builder()
                .name("")
                .content(chatRequest.content())
                .room(entityManager.getReference(Room.class, roomId))
                .build();
        chatRepository.save(chat);
        return chatMapper.toResponse(chat);
    }

    public ChatResponse create(String roomId, ChatInfo chatInfo) {
        var chat = Chat.builder()
                .name(chatInfo.name())
                .content(chatInfo.content())
                .room(entityManager.getReference(Room.class, roomId))
                .build();
        chatRepository.save(chat);
        return chatMapper.toResponse(chat);
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> findAll(String roomId) {
        return chatRepository.findAllByRoomId(roomId).stream()
                .map(chatMapper::toResponse)
                .toList();
    }

    public void delete(String roomId, Long id) {
        chatRepository.deleteByIdAndRoomId(id, roomId);
    }

}
