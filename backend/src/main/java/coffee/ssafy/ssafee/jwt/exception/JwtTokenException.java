package coffee.ssafy.ssafee.jwt.exception;

import org.zalando.problem.AbstractThrowableProblem;

public class JwtTokenException extends AbstractThrowableProblem {

    public JwtTokenException(JwtTokenErrorCode errorCode) {
        super(
                null,
                errorCode.getStatus().name(),
                errorCode.getStatus(),
                errorCode.getDetail()
        );
    }

}
