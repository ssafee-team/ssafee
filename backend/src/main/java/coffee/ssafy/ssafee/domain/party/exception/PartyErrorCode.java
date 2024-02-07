package coffee.ssafy.ssafee.domain.party.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum PartyErrorCode {

    NOT_EXISTS_PARTY(Status.NOT_FOUND, "존재하지 않는 파티입니다."),
    NOT_EXISTS_CREATOR(Status.NOT_FOUND, "존재하지 않는 파티 생성자입니다."),
    NOT_EXISTS_PARTICIPANT(Status.NOT_FOUND, "존재하지 않는 파티 참가자입니다."),
    NOT_EXISTS_ORDER_MENU(Status.NOT_FOUND, "존재하지 않는 주문메뉴입니다."),
    THE_ORDER_AMOUNT_IS_LESS_THAN_THE_MINIMUM_ORDER(Status.BAD_REQUEST, "최소 주문 금액을 충족하지 않습니다.");

    private final Status status;
    private final String detail;

}
