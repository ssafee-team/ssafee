package coffee.ssafy.ssafee.domain.order.exception;

import org.zalando.problem.AbstractThrowableProblem;

public class OrderException extends AbstractThrowableProblem {

    public OrderException(OrderErrorCode errorCode) {
        super(
                null,
                errorCode.getStatus().name(),
                errorCode.getStatus(),
                errorCode.getDetail()
        );
    }

}
