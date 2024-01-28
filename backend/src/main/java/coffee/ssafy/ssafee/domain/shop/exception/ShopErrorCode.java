package coffee.ssafy.ssafee.domain.shop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum ShopErrorCode {

    NOT_EXISTS_SHOP(Status.NOT_FOUND, "존재하지 않는 가게입니다."),
    NOT_EXISTS_MENU(Status.NOT_FOUND, "존재하지 않는 메뉴입니다.");

    private final Status status;
    private final String detail;

}
