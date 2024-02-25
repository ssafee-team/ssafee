package coffee.ssafy.ssafee.domain.room.exception;

import org.zalando.problem.AbstractThrowableProblem;

public class RoomException extends AbstractThrowableProblem {

    public RoomException(RoomErrorCode errorCode) {
        super(
                null,
                errorCode.getStatus().name(),
                errorCode.getStatus(),
                errorCode.getDetail()
        );
    }

}
