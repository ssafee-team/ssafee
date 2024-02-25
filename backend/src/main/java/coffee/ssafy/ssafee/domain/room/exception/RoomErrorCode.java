package coffee.ssafy.ssafee.domain.room.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum RoomErrorCode {

    NOT_FOUND_ROOM(Status.NOT_FOUND, "찾을 수 없는 방입니다."),
    NOT_FOUND_CREATOR(Status.NOT_FOUND, "찾을 수 없는 방 생성자입니다."),
    NOT_FOUND_PARTICIPANT(Status.NOT_FOUND, "찾을 수 없는 방 참가자입니다."),
    NOT_FOUND_CHOICE_MENU(Status.NOT_FOUND, "찾을 수 없는 선택 메뉴입니다."),
    NOT_FOUND_ORDER(Status.NOT_FOUND, "찾을 수 없는 주문 현황입니다."),
    UNAUTHORIZED_USER(Status.UNAUTHORIZED, "해당 방에 대해 권한이 없는 사용자입니다.");

    private final Status status;
    private final String detail;

}
