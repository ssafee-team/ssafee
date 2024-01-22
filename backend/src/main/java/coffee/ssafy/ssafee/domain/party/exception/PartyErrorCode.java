package coffee.ssafy.ssafee.domain.party.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public enum PartyErrorCode {

    NOT_EXISTS_PARTY(Status.NOT_FOUND, "존재하지 않는 방입니다.");

    private final Status status;
    private final String detail;

}
