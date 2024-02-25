package coffee.ssafy.ssafee.domain.user.dto.response;

import lombok.Builder;

@Builder
public record ManagerResponse(
        Long shopId
) {
}
