package coffee.ssafy.ssafee.domain.order.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderResponse(
        Instant createdAt,
        Instant confirmedAt,
        Instant rejectedAt,
        Instant madeAt,
        Instant deliveredAt
) {
}
