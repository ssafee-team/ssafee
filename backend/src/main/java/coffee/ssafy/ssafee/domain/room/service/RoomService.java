package coffee.ssafy.ssafee.domain.room.service;

import coffee.ssafy.ssafee.common.DateUtils;
import coffee.ssafy.ssafee.domain.room.dto.request.CreatorRequest;
import coffee.ssafy.ssafee.domain.room.dto.request.RoomRequest;
import coffee.ssafy.ssafee.domain.room.dto.response.RoomDetailResponse;
import coffee.ssafy.ssafee.domain.room.dto.response.RoomResponse;
import coffee.ssafy.ssafee.domain.room.entity.Creator;
import coffee.ssafy.ssafee.domain.room.entity.Room;
import coffee.ssafy.ssafee.domain.room.exception.RoomErrorCode;
import coffee.ssafy.ssafee.domain.room.exception.RoomException;
import coffee.ssafy.ssafee.domain.room.mapper.RoomMapper;
import coffee.ssafy.ssafee.domain.room.repository.CreatorRepository;
import coffee.ssafy.ssafee.domain.room.repository.RoomRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private static final int ROOM_ID_LENGTH = 10;

    @PersistenceContext
    private final EntityManager entityManager;
    private final RoomRepository roomRepository;
    private final CreatorRepository creatorRepository;
    private final RoomMapper roomMapper;

    public String create(Long userId, RoomRequest roomRequest) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String id = ThreadLocalRandom.current()
                .ints(ROOM_ID_LENGTH, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        Room room = Room.builder()
                .id(id)
                .name(roomRequest.name())
                .generation(roomRequest.generation())
                .classroom(roomRequest.classroom())
                .lastOrderTime(roomRequest.lastOrderTime().atDate(LocalDate.now()).toInstant(DateUtils.ZONE_OFFSET))
                .shop(entityManager.getReference(Shop.class, roomRequest.shopId()))
                .user(entityManager.getReference(User.class, userId))
                .build();
        roomRepository.save(room);
        CreatorRequest creatorRequest = roomRequest.creator();
        String webhookUrl = creatorRequest.mattermostWebhookUrl();
        webhookUrl = webhookUrl != null && webhookUrl
                .startsWith("https://meeting.ssafy.com/hooks/")
                ? webhookUrl : null;
        Creator creator = Creator.builder()
                .name(creatorRequest.name())
                .bank(creatorRequest.bank())
                .account(creatorRequest.account())
                .mattermostWebhookUrl(webhookUrl)
                .room(room)
                .build();
        creatorRepository.save(creator);
        return id;
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findAll(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            startDate = endDate = LocalDate.now();
        }
        return roomRepository.findAllByCreatedAtBetween(startDate, endDate).stream()
                .map(roomMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomDetailResponse find(String id) {
        return roomRepository.findById(id)
                .map(roomMapper::toDetailResponse)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));
    }

    @Transactional(readOnly = true)
    public void validatePrincipal(String id, Long userId) {
        if (!roomRepository.existsByIdAndUserId(id, userId)) {
            throw new RoomException(RoomErrorCode.UNAUTHORIZED_USER);
        }
    }

}
