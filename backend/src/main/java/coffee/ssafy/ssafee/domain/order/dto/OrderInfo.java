package coffee.ssafy.ssafee.domain.order.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderInfo(
        Instant createdAt,
        Instant confirmedAt,
        Instant rejectedAt,
        Instant madeAt,
        Instant deliveredAt,
        String roomId,
        Long shopId
) {
}
