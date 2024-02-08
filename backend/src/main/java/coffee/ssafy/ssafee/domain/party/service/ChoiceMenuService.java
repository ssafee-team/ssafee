package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.ChoiceMenuCreateRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.entity.*;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.ChoiceMenuMapper;
import coffee.ssafy.ssafee.domain.party.repository.ChoiceMenuRepository;
import coffee.ssafy.ssafee.domain.party.repository.ParticipantRepository;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
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
    private final PartyRepository partyRepository;
    private final ParticipantRepository participantRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final ChoiceMenuMapper choiceMenuMapper;

    public Long createChoiceMenu(String accessCode, ChoiceMenuCreateRequest choiceMenuCreateRequest) {
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        Menu menuReference = entityManager.getReference(Menu.class, choiceMenuCreateRequest.menuId());

        Participant participant = participantRepository.findByPartyIdAndName(party.getId(), choiceMenuCreateRequest.participantName())
                .orElseGet(() -> Participant.builder()
                        .name(choiceMenuCreateRequest.participantName())
                        .party(party)
                        .build());
        participantRepository.save(participant);

        ChoiceMenu choiceMenu = ChoiceMenu.builder()
                .menu(menuReference)
                .participant(participant)
                .party(party)
                .build();
        choiceMenu.setChoiceMenuOptionCategories(choiceMenuCreateRequest.optionCategories().stream()
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

    public List<ChoiceMenuResponse> findChoiceMenus(Long partyId) {
        return choiceMenuRepository.findAllByPartyId(partyId).stream()
                .map(choiceMenuMapper::toDto)
                .toList();
    }

    public void deleteChoiceMenu(Long partyId, Long choiceMenuId) {
        choiceMenuRepository.deleteByPartyIdAndId(partyId, choiceMenuId);
    }

}
