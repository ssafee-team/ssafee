package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailForManagerResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.dto.response.PartyInfoForManagerResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopOrderMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopOrderRepository;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
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
    private final ShopRepository shopRepository;
    private final PartyRepository partyRepository;
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

    public void orderConfirm(Long shopId, Long partyId) {
        // 1. 유효한 파티인가?
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: Rejected field == null?
        // 3. confirmed_time 을 현재 시간으로 업데이트
        party.confirm();
        partyRepository.save(party);
    }

    public void orderReject(Long shopId, Long partyId) {
        // 1. 유효한 파티인가?
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));

        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: confirmed field == null?
        // 3. rejected_time 을 현재 시간으로 업데이트
        party.reject();
        partyRepository.save(party);
    }

    public void orderMade(Long shopId, Long partyId) {
        // 1. 유효한 파티인가?
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));

        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: confirmed field == null?
        // 3. made_time 을 현재 시간으로 업데이트
        party.make();
        partyRepository.save(party);
    }

    public void orderStartDelivery(Long shopId, Long partyId) {
        // 1. 유효한 파티인가?
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));

        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: made field == null?
    }

}
