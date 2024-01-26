package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.ChosenOptionCategoryRequest;
import coffee.ssafy.ssafee.domain.party.dto.request.OrderMenuRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.party.entity.*;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.OrderMenuRequestMapper;
import coffee.ssafy.ssafee.domain.party.mapper.ParticipantResponseMapper;
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
    private final ParticipantResponseMapper participantResponseMapper;
    private final OrderMenuRequestMapper orderMenuRequestMapper;

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

        OrderMenu orderMenu = orderMenuRequestMapper.toEntity(orderMenuRequest);
        orderMenu.prepareCreation(party, menuReference, participant);

        List<ChosenOptionCategoryRequest> optionCategories = orderMenuRequest.optionCategories();
        List<OrderMenuOptionCategory> orderMenuOptionCategories = optionCategories.stream()
                .map(c -> OrderMenuOptionCategory.builder()
                        .orderMenu(orderMenu)
                        .optionCategory(entityManager.getReference(OptionCategory.class, c.optionCategoryId()))
                        .build())
                .toList();
        for (int i = 0; i < orderMenuOptionCategories.size(); i++) {
            OrderMenuOptionCategory orderMenuOptionCategory = orderMenuOptionCategories.get(i);
            List<OrderMenuOption> orderMenuOptions = optionCategories.get(i).options().stream()
                    .map(o -> OrderMenuOption.builder()
                            .orderMenuOptionCategory(orderMenuOptionCategory)
                            .option(entityManager.getReference(Option.class, o.optionId()))
                            .build())
                    .toList();
            orderMenuOptionCategory.setOrderMenuOptions(orderMenuOptions);
        }
        orderMenu.setOrderMenuOptionCategories(orderMenuOptionCategories);
        orderMenuRepository.save(orderMenu);

        return orderMenu.getId();
    }

    public List<ParticipantResponse> findOrderMenusByAccessCode(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY))
                .getParticipants().stream()
                .map(participantResponseMapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteOrderMenuByAccessCodeAndId(String accessCode, Long id) {
        Long partyId = partyRepository.findIdByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        orderMenuRepository.deleteByPartyIdAndId(partyId, id);
    }

}
