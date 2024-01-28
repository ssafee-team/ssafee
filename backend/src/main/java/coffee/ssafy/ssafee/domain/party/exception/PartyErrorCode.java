package coffee.ssafy.ssafee.domain.party.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum PartyErrorCode {

    NOT_EXISTS_PARTY(Status.NOT_FOUND, "존재하지 않는 파티입니다."),
    NOT_EXISTS_PARTY_OR_ORDER_MENU(Status.NOT_FOUND, "존재하지 않는 파티 혹은 주문메뉴입니다."),
    NOT_EXISTS_CREATOR(Status.NOT_FOUND, "존재하지 않는 파티 생성자입니다."),
    NOT_EXISTS_PARTICIPANT(Status.NOT_FOUND, "존재하지 않는 파티 참가자입니다.");

    private final Status status;
    private final String detail;

}
