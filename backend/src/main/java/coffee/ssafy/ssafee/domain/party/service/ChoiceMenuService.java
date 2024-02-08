package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.ChoiceMenuRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.entity.*;
import coffee.ssafy.ssafee.domain.party.mapper.ChoiceMenuMapper;
import coffee.ssafy.ssafee.domain.party.repository.ChoiceMenuRepository;
import coffee.ssafy.ssafee.domain.party.repository.ParticipantRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChoiceMenuService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyService partyService;
    private final ParticipantRepository participantRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final ChoiceMenuMapper choiceMenuMapper;

    public Long create(String accessCode, ChoiceMenuRequest choiceMenuRequest) {
        Long partyId = partyService.findPartyIdByAccessCode(accessCode);
        Party party = entityManager.getReference(Party.class, partyId);
        Menu menuReference = entityManager.getReference(Menu.class, choiceMenuRequest.menuId());

        Participant participant = participantRepository.findByPartyIdAndName(partyId, choiceMenuRequest.participantName())
                .orElseGet(() -> Participant.builder()
                        .name(choiceMenuRequest.participantName())
                        .party(party)
                        .build());
        participantRepository.save(participant);

        ChoiceMenu choiceMenu = ChoiceMenu.builder()
                .menu(menuReference)
                .participant(participant)
                .party(party)
                .build();
        choiceMenu.setChoiceMenuOptionCategories(choiceMenuRequest.optionCategories().stream()
                .map(optionCategoryRequest -> {
                    ChoiceMenuOptionCategory choiceMenuOptionCategory = ChoiceMenuOptionCategory.builder()
                            .choiceMenu(choiceMenu)
                            .optionCategory(entityManager.getReference(OptionCategory.class, optionCategoryRequest.optionCategoryId()))
                            .build();
                    choiceMenuOptionCategory.setChoiceMenuOptions(optionCategoryRequest.optionIds().stream()
                            .map(optionId -> ChoiceMenuOption.builder()
                                    .choiceMenuOptionCategory(choiceMenuOptionCategory)
                                    .option(entityManager.getReference(Option.class, optionId))
                                    .build())
                            .toList());
                    return choiceMenuOptionCategory;
                })
                .toList());
        choiceMenuRepository.save(choiceMenu);
        return choiceMenu.getId();
    }

    public List<ChoiceMenuResponse> findOrderMenusByAccessCode(String accessCode) {
        Long partyId = partyService.findPartyIdByAccessCode(accessCode);
        return choiceMenuRepository.findAllByPartyId(partyId).stream()
                .map(choiceMenuMapper::toDto)
                .toList();
    }

    public void deleteOrderMenuByAccessCodeAndId(String accessCode, Long choiceMenuId) {
        Long partyId = partyService.findPartyIdByAccessCode(accessCode);
        choiceMenuRepository.deleteByPartyIdAndId(partyId, choiceMenuId);
    }

}
