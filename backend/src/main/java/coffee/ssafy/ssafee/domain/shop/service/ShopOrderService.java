package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.party.dto.response.OrderResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.shop.dto.response.PartyInfoForManagerResponse;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopOrderMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopOrderService {

    private final EntityManager entityManager;
    private final ShopOrderRepository shopOrderRepository;
    private final ShopOrderMapper shopOrderMapper;

    public List<PartyInfoForManagerResponse> getShopHistories(Long shopId) {
        return shopOrderRepository.findAllByShopIdAndOrderDelivered(shopId).stream()
                .filter(party -> Boolean.TRUE.equals(party.getOrder().getDelivered()))
                .map(this::convertToPartyInfoForManagerResponse)
                .toList();
    }
    private PartyInfoForManagerResponse convertToPartyInfoForManagerResponse(Party party) {
        return new PartyInfoForManagerResponse(
                party.getId(),
                party.getName(),
                party.getShop().getId(),
                party.getOrder().getId()
        );
    }

}
