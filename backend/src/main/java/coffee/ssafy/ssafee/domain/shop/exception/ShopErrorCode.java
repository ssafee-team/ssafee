package coffee.ssafy.ssafee.domain.shop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum ShopErrorCode {

    NOT_EXISTS_SHOP(Status.NOT_FOUND, "존재하지 않는 가게입니다."),
    NOT_EXISTS_MENU_CATEGORY(Status.NOT_FOUND, "존재하지 않는 메뉴 카테고리입니다."),
    NOT_EXISTS_MENU(Status.NOT_FOUND, "존재하지 않는 메뉴입니다."),
    NOT_EXISTS_OPTION_CATEGORY(Status.NOT_FOUND, "존재하지 않는 옵션 카테고리입니다."),
    NOT_EXISTS_OPTION(Status.NOT_FOUND, "존재하지 않는 옵션입니다."),
    File_IS_EMPTY(Status.BAD_REQUEST, "파일이 비어있습니다."),
    FAILED_UPLOAD_IMAGE(Status.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");

    private final Status status;
    private final String detail;

}
