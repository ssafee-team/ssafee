package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.IsCarrierResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyStatusResponse;
import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.party.entity.Participant;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.PartyMapper;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class PartyOrderService {

    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;
    private final MatterMostService matterMostService;

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

    public PartyStatusResponse getOrders(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .map(partyMapper::toPartyStatus)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
    }

    public void orderDelivered(String accessCode) {
        // 검증
        // 1. 유효한 엑세스 코드인가?
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        // TODO: 배달시작 필드가 null인지 검사
        party.updateDeliveredTime(LocalDateTime.now());
        if (party.getCreator().getWebhookUrl() != null) {
            matterMostService.sendMMNotification(party.getCreator().getWebhookUrl(), "@here @고영훈 커피 배달 완료");
        }
    }

    public void giveMeMoney(String accessCode) {
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));

        if (party.getCreator().getWebhookUrl() != null) {
            List<Participant> participants = party.getParticipants();

            StringBuilder sb = new StringBuilder();
            sb.append("### :loopy: SSAFEE NOTICE :loopy: \n\n");
            sb.append("#### 파티명: " + party.getName() + "\n\n");
            sb.append("@here \n");
            sb.append("#### 송금 바랍니다 \n");
            sb.append("#### " + party.getCreator().getBank() + "(" + party.getCreator().getName() + "): " + party.getCreator().getAccount() + "\n\n");
            sb.append("| 이름 | 주문메뉴 | 금액 |\n");
            sb.append("|:-----------:|:-----------:|:-----------:|\n");

            for (Participant p : participants) {
                System.out.println(p.getName());
                Integer price = p.getChoiceMenus().stream()
                        .mapToInt(choiceMenu -> choiceMenu.getMenu().getPrice() + choiceMenu.getChoiceMenuOptionCategories().stream()
                                .flatMap(choiceOptionCategory -> choiceOptionCategory.getChoiceMenuOptions().stream())
                                .mapToInt(choiceOption -> choiceOption.getOption().getPrice())
                                .sum())
                        .sum();
                if (!p.getPaid()) {
                    sb.append("| ");
                    sb.append(p.getName());
                    sb.append(" | ");
                    sb.append(p.getChoiceMenus().stream().map(choiceMenu -> choiceMenu.getMenu().getName()).collect(Collectors.joining(", ")));
                    sb.append(" | ");
                    sb.append(price);
                    sb.append(" |\n");
                }
            }
            matterMostService.sendMMNotification(party.getCreator().getWebhookUrl(), sb.toString());
        }
    }

    public void sendAdvertise(String accessCode) {
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        // 파티 access 코드를 webhook url로 발송한다.
        // [초대 링크]()
        String inviteUrl = "https://ssafy.coffee/room/" + accessCode;
        StringBuilder sb;
        if (party.getCreator().getWebhookUrl() != null) {
            sb = new StringBuilder();
            sb.append("## :alert_siren: SSAFEE NOTICE :alert_siren: \n");
            sb.append("@here \n");
            sb.append("#### 새로운 커피파티가 개설되었습니다. \n");
            sb.append("#### 카페: " + party.getShop().getName() + "\n");
            sb.append("#### 마감시간: " + party.getLastOrderTime() + " \n");
            sb.append("#### :link: [" + party.getName() + "](" + inviteUrl + ") \n");
            matterMostService.sendMMNotification(party.getCreator().getWebhookUrl(), sb.toString());
        }

    }

    public List<String> isCarrier(String accessCode) {
        List<IsCarrierResponse> carrierCandidateList = partyRepository.findByAccessCode(accessCode).stream()
                .map(partyMapper::toIsCarrierDto)
                .collect(Collectors.toList());
        int count = carrierCandidateList.size() / 6;
        Random random = new Random();

        // 랜덤 인덱스를 생성하고 바로 해당 인덱스의 요소로 변환
        List<String> realCarrierList = IntStream.generate(() -> random.nextInt(carrierCandidateList.size()))
                .distinct()
                .limit(count)
                .mapToObj(carrierCandidateList::get)
                .map(IsCarrierResponse::name) // 실제 필드 이름으로 변경 필요
                .collect(Collectors.toList());

        return realCarrierList;
    }

}
