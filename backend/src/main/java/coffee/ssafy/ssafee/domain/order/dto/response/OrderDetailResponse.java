package coffee.ssafy.ssafee.domain.order.dto.response;

import coffee.ssafy.ssafee.domain.room.dto.response.ChoiceMenuResponse;

import java.time.Instant;
import java.util.List;

public record OrderDetailResponse(
        Instant createdAt,
        Instant confirmedAt,
        Instant rejectedAt,
        Instant madeAt,
        Instant deliveredAt,
        List<ChoiceMenuResponse> choiceMenus
) {
}
