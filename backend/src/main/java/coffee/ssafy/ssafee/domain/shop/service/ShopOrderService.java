package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailForManagerResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.shop.dto.response.PartyInfoForManagerResponse;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopOrderMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopOrderRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopOrderService {

    private final EntityManager entityManager;
    private final ShopOrderRepository shopOrderRepository;
    private final ShopOrderMapper shopOrderMapper;

    public List<PartyInfoForManagerResponse> getShopHistories(Long shopId) {
        return shopOrderRepository.findAllByShopIdAndDeliveredTimeIsNotNull(shopId).stream()
                .filter(party -> party.getDeliveredTime() != null)
                .map(this::convertToPartyInfoForManagerResponse)
                .toList();
    }

    private PartyInfoForManagerResponse convertToPartyInfoForManagerResponse(Party party) {
        return new PartyInfoForManagerResponse(
                party.getId(),
                party.getName(),
                party.getShop().getId()
        );
    }

    public List<PartyDetailForManagerResponse> getShopHistoriesDetail(Long shopId, Long partyId) {
        return shopOrderRepository.findAllById(partyId).stream()
                .map(this::convertToPartyDetailForManagerResponse)
                .toList();
    }

    private PartyDetailForManagerResponse convertToPartyDetailForManagerResponse(Party party) {
        return new PartyDetailForManagerResponse(
                party.getId(),
                party.getName(),
                party.getShop().getId(),
                party.getRealOrderedTime(),
                party.getDeliveredTime(),
                party.getCreator().getName(),
                party.getChoiceMenus()
        );
    }

}
