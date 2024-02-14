package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.ChoiceMenuCreateRequest;
import coffee.ssafy.ssafee.domain.party.dto.request.ChoiceOptionCategoryRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.entity.*;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.ChoiceMenuMapper;
import coffee.ssafy.ssafee.domain.party.repository.*;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class ChoiceMenuService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyRepository partyRepository;
    private final ParticipantRepository participantRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final ChoiceMenuOptionCategoryRepository choiceMenuOptionCategoryRepository;
    private final ChoiceMenuOptionRepository choiceMenuOptionRepository;
    private final ChoiceMenuMapper choiceMenuMapper;

    public Long createChoiceMenu(String accessCode, ChoiceMenuCreateRequest choiceMenuCreateRequest) {
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        Menu menuReference = entityManager.getReference(Menu.class, choiceMenuCreateRequest.menuId());

        Participant participant = participantRepository.findByPartyIdAndName(party.getId(), choiceMenuCreateRequest.participantName())
                .orElseGet(() -> participantRepository.save(Participant.builder()
                        .name(choiceMenuCreateRequest.participantName())
                        .party(party)
                        .build()));

        ChoiceMenu choiceMenu = ChoiceMenu.builder()
                .menu(menuReference)
                .participant(participant)
                .party(party)
                .build();
        choiceMenuRepository.save(choiceMenu);

        List<ChoiceOptionCategoryRequest> choiceMenuOptionCategoryRequests = choiceMenuCreateRequest.optionCategories();
        List<ChoiceMenuOptionCategory> choiceMenuOptionCategories = choiceMenuOptionCategoryRequests.stream()
                .map(choiceOptionCategoryRequest -> ChoiceMenuOptionCategory.builder()
                        .choiceMenu(choiceMenu)
                        .optionCategory(entityManager.getReference(OptionCategory.class, choiceOptionCategoryRequest.optionCategoryId()))
                        .build())
                .toList();
        choiceMenuOptionCategoryRepository.saveAll(choiceMenuOptionCategories);

        List<ChoiceMenuOption> choiceMenuOptions = IntStream
                .range(0, choiceMenuOptionCategories.size())
                .mapToObj(i -> choiceMenuOptionCategoryRequests.get(i).optionIds().stream()
                        .map(optionId -> ChoiceMenuOption.builder()
                                .choiceMenuOptionCategory(choiceMenuOptionCategories.get(i))
                                .option(entityManager.getReference(Option.class, optionId))
                                .build()))
                .flatMap(x -> x)
                .toList();
        choiceMenuOptionRepository.saveAll(choiceMenuOptions);
        return choiceMenu.getId();
    }

    @Transactional(readOnly = true)
    public List<ChoiceMenuResponse> findChoiceMenus(Long partyId) {
        return choiceMenuRepository.findAllByPartyId(partyId).stream()
                .map(choiceMenuMapper::toResponse)
                .toList();
    }

    public void deleteChoiceMenu(Long partyId, Long choiceMenuId) {
        choiceMenuRepository.deleteByPartyIdAndId(partyId, choiceMenuId);
    }

}
