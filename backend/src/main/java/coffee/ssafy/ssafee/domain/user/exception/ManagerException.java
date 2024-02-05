package coffee.ssafy.ssafee.domain.user.exception;

import org.zalando.problem.AbstractThrowableProblem;

public class ManagerException extends AbstractThrowableProblem {

    public ManagerException(ManagerErrorCode errorCode) {
        super(
                null,
                errorCode.getStatus().name(),
                errorCode.getStatus(),
                errorCode.getDetail()
        );
    }

}
