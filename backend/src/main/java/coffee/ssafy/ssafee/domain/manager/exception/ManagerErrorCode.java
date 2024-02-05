package coffee.ssafy.ssafee.domain.manager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum ManagerErrorCode {

    NOT_FOUND_MANAGER(Status.NOT_FOUND, "해당 매니저를 찾을 수 없습니다."),
    INVALID_ID_OR_PASSWORD(Status.UNAUTHORIZED, "매니저 아이디 또는 비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_SHOP(Status.UNAUTHORIZED, "해당 가게에 대한 권한이 없습니다.");

    private final Status status;
    private final String detail;

}
