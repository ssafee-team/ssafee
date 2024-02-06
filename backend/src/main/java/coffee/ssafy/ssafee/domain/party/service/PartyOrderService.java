package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PartyOrderService {

    private final PartyRepository partyRepository;
    private final ShopRepository shopRepository;

    public Long createOrder(String accessCode) {
        // 검증
        // 1. 유효한 엑세스 코드인가?
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));

        // 2. 마감시간 전인가 후인가?
        party.updateRealOrderedTime(LocalDateTime.now());
        partyRepository.save(party);

        // 3. 최소주문금액을 넘었는가?
        Integer minimumPrice = party.getShop().getMinimumPrice();
        int total = 0;
        for(ChoiceMenu menu : party.getChoiceMenus()) {
            total += menu.getMenu().getPrice();
        }
        if (total < minimumPrice) {
            throw new PartyException(PartyErrorCode.THE_ORDER_AMOUNT_IS_LESS_THAN_THE_MINIMUM_ORDER);
        }
        return party.getId();
    }

    public Long orderDelivered(String accessCode) {
        // 검증
        // 1. 유효한 엑세스 코드인가?
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        // TODO: 배달시작 필드가 null인지 검사
        party.updateDeliveredTime(LocalDateTime.now());
        return party.getId();
    }

    //
    public void sendMattermostNotification(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        String webHookUrl = party.getCreator().getWebhookUrl();

    }

}
