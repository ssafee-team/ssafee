package coffee.ssafy.ssafee.domain.room.service;

import coffee.ssafy.ssafee.domain.room.dto.request.ChoiceMenuCreateRequest;
import coffee.ssafy.ssafee.domain.room.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceOption;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceOptionCategory;
import coffee.ssafy.ssafee.domain.room.entity.Participant;
import coffee.ssafy.ssafee.domain.room.exception.RoomErrorCode;
import coffee.ssafy.ssafee.domain.room.exception.RoomException;
import coffee.ssafy.ssafee.domain.room.mapper.ChoiceMenuMapper;
import coffee.ssafy.ssafee.domain.room.repository.ChoiceMenuRepository;
import coffee.ssafy.ssafee.domain.room.repository.ParticipantRepository;
import coffee.ssafy.ssafee.domain.room.repository.RoomRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class ChoiceMenuService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final ChoiceMenuMapper choiceMenuMapper;

    public ChoiceMenuResponse create(String roomId, ChoiceMenuCreateRequest choiceMenuCreateRequest) {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));
        var participant = participantRepository.findByRoomIdAndName(room.getId(), choiceMenuCreateRequest.participantName())
                .orElseGet(() -> participantRepository.save(Participant.builder()
                        .name(choiceMenuCreateRequest.participantName())
                        .room(room)
                        .build()));
        var menuReference = entityManager.getReference(Menu.class, choiceMenuCreateRequest.menuId());

        ChoiceMenu choiceMenu = ChoiceMenu.builder()
                .menu(menuReference)
                .room(room)
                .participant(participant)
                .choiceOptionCategories(new ArrayList<>())
                .build();

        var choiceOptionCategories = choiceMenu.getChoiceOptionCategories();
        var choiceOptionCategoryRequests = choiceMenuCreateRequest.optionCategories();

        choiceOptionCategoryRequests.stream()
                .map(choiceOptionCategoryRequest -> ChoiceOptionCategory.builder()
                        .room(room)
                        .choiceMenu(choiceMenu)
                        .optionCategory(entityManager.getReference(OptionCategory.class, choiceOptionCategoryRequest.optionCategoryId()))
                        .choiceOptions(new ArrayList<>())
                        .build())
                .forEach(choiceOptionCategories::add);

        IntStream.range(0, choiceOptionCategoryRequests.size())
                .mapToObj(i -> Pair.of(choiceOptionCategories.get(i), choiceOptionCategoryRequests.get(i)))
                .forEach(pair -> pair.getSecond().optionIds().stream()
                        .map(optionId -> ChoiceOption.builder()
                                .room(room)
                                .choiceOptionCategory(pair.getFirst())
                                .option(entityManager.getReference(Option.class, optionId))
                                .build())
                        .forEach(choiceOption -> pair.getFirst().getChoiceOptions().add(choiceOption)));
        choiceMenuRepository.save(choiceMenu);
        return choiceMenuMapper.toResponse(choiceMenu);
    }

    @Transactional(readOnly = true)
    public List<ChoiceMenuResponse> findAll(String roomId) {
        return choiceMenuRepository.findAllByRoomId(roomId).stream()
                .map(choiceMenuMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChoiceMenuResponse find(String roomId, Long choiceMenuId) {
        return choiceMenuRepository.findByRoomIdAndId(roomId, choiceMenuId)
                .map(choiceMenuMapper::toResponse)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_CHOICE_MENU));
    }

    public ChoiceMenuResponse delete(String roomId, Long choiceMenuId) {
        var choiceMenu = choiceMenuRepository.findByRoomIdAndId(roomId, choiceMenuId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_CHOICE_MENU));
        var participantId = choiceMenu.getParticipant().getId();

        choiceMenuRepository.delete(choiceMenu);
        if (!choiceMenuRepository.existsByParticipantId(participantId)) {
            participantRepository.deleteById(participantId);
        }
        return choiceMenuMapper.toResponse(choiceMenu);
    }

}
