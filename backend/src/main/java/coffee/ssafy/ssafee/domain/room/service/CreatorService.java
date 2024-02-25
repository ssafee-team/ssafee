package coffee.ssafy.ssafee.domain.room.service;

import coffee.ssafy.ssafee.domain.room.entity.Participant;
import coffee.ssafy.ssafee.domain.room.exception.RoomErrorCode;
import coffee.ssafy.ssafee.domain.room.exception.RoomException;
import coffee.ssafy.ssafee.domain.room.repository.ChoiceMenuRepository;
import coffee.ssafy.ssafee.domain.room.repository.ParticipantRepository;
import coffee.ssafy.ssafee.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatorService {

    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final MattermostService mattermostService;

    @Transactional(readOnly = true)
    public void advertise(String id) {
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));

        var mattermostWebhookUrl = room.getCreator().getMattermostWebhookUrl();
        if (mattermostWebhookUrl == null) {
            return;
        }
        var inviteUrl = "https://ssafy.coffee/room/" + id;
        var message = "## :alert_siren: SSAFEE NOTICE :alert_siren: \n" +
                      "@here \n" +
                      "#### 새로운 커피파티가 개설되었습니다. \n" +
                      "#### 카페: " + room.getShop().getName() + "\n" +
                      "#### 마감시간: " + room.getLastOrderTime() + " \n" +
                      "#### :link: [" + room.getName() + "](" + inviteUrl + ") \n";
        mattermostService.sendMMNotification(mattermostWebhookUrl, message);
    }

    @Transactional(readOnly = true)
    public void noticeCarriers(String roomId) {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));

        var mattermostWebhookUrl = room.getCreator().getMattermostWebhookUrl();
        if (mattermostWebhookUrl == null) {
            return;
        }
        var participants = participantRepository.findAllByRoomIdAndPickedCarrierIsFalse(roomId);

        var sb = new StringBuilder();
        sb.append("## :fire_parrot: SSAFEE NOTICE :fire_parrot: \n");
        sb.append("@here \n\n");
        sb.append("| 배달부 명단 | \n");
        sb.append("|: --- :| \n");
        for (Participant p : participants) {
            sb.append("| " + p.getName() + " |\n");
        }
        sb.append(":thanggu9_1:  :thanggu9_2:  :thanggu12:  :thanggu13:  :thanggu14: \n");
        mattermostService.sendMMNotification(mattermostWebhookUrl, sb.toString());
    }

    public void noticeDelivered(String id) {
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));
        // TODO: 배달시작 필드가 null인지 검사
        // room.updateDeliver();
        var mattermostWebhookUrl = room.getCreator().getMattermostWebhookUrl();
        if (mattermostWebhookUrl == null) {
            return;
        }
        var message = "## :alert_siren: SSAFEE NOTICE :alert_siren: \n" +
                      "@here \n" +
                      "#### 주문하신 음료가 도착했습니다. \n" +
                      "#### 파티명: " + room.getName() + "\n" +
                      "#### 총  무: " + room.getCreator().getName() + " \n\n" +
                      "#### 본인의 음료를 수령해주세요";
        mattermostService.sendMMNotification(mattermostWebhookUrl, message);
    }

    @Transactional(readOnly = true)
    public void giveMeMoney(String roomId) {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));

        var mattermostWebhookUrl = room.getCreator().getMattermostWebhookUrl();
        if (mattermostWebhookUrl == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("### :loopy: SSAFEE NOTICE :loopy: \n\n");
        sb.append("#### 파티명: " + room.getName() + "\n\n");
        sb.append("@here \n");
        sb.append("#### 송금 바랍니다 \n");
        sb.append("#### " + room.getCreator().getBank() + "(" + room.getCreator().getName() + "): " + room.getCreator().getAccount() + "\n\n");
        sb.append("| 이름 | 주문메뉴 | 금액 |\n");
        sb.append("|:-----------:|:-----------:|:-----------:|\n");

        var participants = participantRepository.findAllByRoomIdAndPaidIsFalse(roomId);
        for (Participant p : participants) {
            var choiceMenus = choiceMenuRepository.findAllByParticipantId(p.getId());
            var price = choiceMenus.stream()
                    .mapToInt(choiceMenu -> choiceMenu.getMenu().getPrice() + choiceMenu.getChoiceOptionCategories().stream()
                            .flatMap(choiceOptionCategory -> choiceOptionCategory.getChoiceOptions().stream())
                            .mapToInt(choiceOption -> choiceOption.getOption().getPrice())
                            .sum())
                    .sum();
            if (!p.getPaid()) {
                sb.append("| ");
                sb.append(p.getName());
                sb.append(" | ");
                sb.append(choiceMenus.stream()
                        .map(choiceMenu -> choiceMenu.getMenu().getName())
                        .collect(Collectors.joining(", ")));
                sb.append(" | ");
                sb.append(price);
                sb.append(" |\n");
            }
        }
        mattermostService.sendMMNotification(mattermostWebhookUrl, sb.toString());
    }

}
