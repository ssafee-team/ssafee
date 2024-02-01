package coffee.ssafy.ssafee.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum JwtTokenErrorCode {

    EXPIRED_TOKEN(Status.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN(Status.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

    private final Status status;
    private final String detail;

}
