package coffee.ssafy.ssafee.domain.shop.exception;

import org.zalando.problem.AbstractThrowableProblem;

public class ShopException extends AbstractThrowableProblem {

    public ShopException(ShopErrorCode errorCode) {
        super(
                null,
                errorCode.getStatus().name(),
                errorCode.getStatus(),
                errorCode.getDetail()
        );
    }

}
