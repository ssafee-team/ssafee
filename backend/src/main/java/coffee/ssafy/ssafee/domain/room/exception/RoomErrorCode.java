package coffee.ssafy.ssafee.domain.room.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum RoomErrorCode {

    NOT_EXISTS_ROOM(Status.NOT_FOUND, "존재하지 않는 방입니다.");

    private final Status status;
    private final String detail;

}
