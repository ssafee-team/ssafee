package coffee.ssafy.ssafee.domain.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {

    ALREADY_ORDERED(Status.BAD_REQUEST, "이미 주문했습니다."),
    LESS_THAN_MINIMUM_PRICE(Status.BAD_REQUEST, "주문 금액이 최소 주문 금액보다 적습니다.");

    private final Status status;
    private final String detail;

}
