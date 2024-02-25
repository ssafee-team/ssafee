package coffee.ssafy.ssafee.domain.user.exception;

import org.zalando.problem.AbstractThrowableProblem;

public class UserException extends AbstractThrowableProblem {

    public UserException(UserErrorCode errorCode) {
        super(
                null,
                errorCode.getStatus().name(),
                errorCode.getStatus(),
                errorCode.getDetail()
        );
    }

}
