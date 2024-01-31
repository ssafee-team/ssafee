package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.OrderMenuRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import coffee.ssafy.ssafee.domain.party.entity.*;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.OrderMenuMapper;
import coffee.ssafy.ssafee.domain.party.repository.OrderMenuRepository;
import coffee.ssafy.ssafee.domain.party.repository.ParticipantRepository;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderMenuService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyRepository partyRepository;
    private final ParticipantRepository participantRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuMapper orderMenuMapper;

    @Transactional
    public Long createOrderMenu(String accessCode, OrderMenuRequest orderMenuRequest) {
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        Menu menuReference = entityManager.getReference(Menu.class, orderMenuRequest.menuId());

        Participant participant = Participant.builder()
                .name(orderMenuRequest.participantName())
                .isCreator(false)
                .party(party)
                .build();
        participantRepository.save(participant);

        OrderMenu orderMenu = OrderMenu.builder()
                .menu(menuReference)
                .participant(participant)
                .party(party)
                .build();
        orderMenu.setOrderMenuOptionCategories(orderMenuRequest.optionCategories().stream()
                .map(chosenOptionCategoryRequest -> {
                    OrderMenuOptionCategory orderMenuOptionCategory = OrderMenuOptionCategory.builder()
                            .orderMenu(orderMenu)
                            .optionCategory(entityManager.getReference(OptionCategory.class, chosenOptionCategoryRequest.optionCategoryId()))
                            .build();
                    orderMenuOptionCategory.setOrderMenuOptions(chosenOptionCategoryRequest.optionIds().stream()
                            .map(optionId -> OrderMenuOption.builder()
                                    .orderMenuOptionCategory(orderMenuOptionCategory)
                                    .option(entityManager.getReference(Option.class, optionId))
                                    .build())
                            .toList());
                    return orderMenuOptionCategory;
                })
                .toList());
        orderMenuRepository.save(orderMenu);

        return orderMenu.getId();
    }

    public List<OrderMenuResponse> findOrderMenusByAccessCode(String accessCode) {
        return orderMenuMapper.toDtoList(partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY))
                .getOrderMenus());
    }

    @Transactional
    public void deleteOrderMenuByAccessCodeAndId(String accessCode, Long id) {
        Long partyId = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY))
                .getId();
        orderMenuRepository.deleteByPartyIdAndId(partyId, id);
    }

}
