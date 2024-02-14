package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.PartyOrderCreateInfo;
import coffee.ssafy.ssafee.domain.party.dto.PartyStatusInfo;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyStatusResponse;
import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.party.entity.Participant;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.PartyOrderMapper;
import coffee.ssafy.ssafee.domain.party.repository.ParticipantRepository;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PartyOrderService {

    private final MatterMostService matterMostService;
    private final PartyRepository partyRepository;
    private final ParticipantRepository participantRepository;
    private final PartyOrderMapper partyOrderMapper;

    public PartyOrderCreateInfo createOrder(String accessCode) {
        // 검증
        // 1. 유효한 엑세스 코드인가?
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));

        // 2. 마감시간 전인가 후인가?
        party.realOrder();

        // 3. 최소주문금액을 넘었는가?
        Integer minimumPrice = party.getShop().getMinimumPrice();
        int total = 0;
        for (ChoiceMenu menu : party.getChoiceMenus()) {
            total += menu.getMenu().getPrice();
        }
        if (total < minimumPrice) {
            throw new PartyException(PartyErrorCode.THE_ORDER_AMOUNT_IS_LESS_THAN_THE_MINIMUM_ORDER);
        }
        return PartyOrderCreateInfo.builder()
                .partyId(party.getId())
                .shopId(party.getShop().getId())
                .build();
    }

    public PartyStatusResponse getOrderStatus(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .map(partyOrderMapper::toResponse)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
    }

    public PartyStatusInfo getOrderStatus(Long partyId) {
        return partyRepository.findById(partyId)
                .map(partyOrderMapper::toInfo)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
    }

    public void orderDelivered(String accessCode) {
        // 검증
        // 1. 유효한 엑세스 코드인가?
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        // TODO: 배달시작 필드가 null인지 검사
        party.deliver();
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

    public void sendCarrierResult(String accessCode) {
        Party party = partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        StringBuilder sb;
        if (party.getCreator().getWebhookUrl() != null) {
            List<Participant> participants = party.getParticipants();

            sb = new StringBuilder();
            sb.append("## :fire_parrot: SSAFEE NOTICE :fire_parrot: \n");
            sb.append("@here \n\n");
            sb.append("| 배달부 명단 | \n");
            sb.append("|: --- :| \n");
            for (Participant p : participants) {
                if (p.getIsCarrier()) {
                    sb.append("| ");
                    sb.append(p.getName());
                    sb.append(" |\n");
                }
            }
            sb.append(":thanggu9_1:  :thanggu9_2:  :thanggu12:  :thanggu13:  :thanggu14: \n");
            matterMostService.sendMMNotification(party.getCreator().getWebhookUrl(), sb.toString());
        }
    }

    public void pickCarrier(Long partyId) {
        List<Participant> participants = participantRepository.findAllByPartyId(partyId);
        int carrierCount = (participants.size() + 5) / 6;
        Collections.shuffle(participants);
        participants.subList(0, carrierCount).forEach(Participant::updateIsCarrier);
    }

    public boolean existsCarrier(Long partyId) {
        return participantRepository.existsByPartyIdAndIsCarrierIsTrue(partyId);
    }

}
