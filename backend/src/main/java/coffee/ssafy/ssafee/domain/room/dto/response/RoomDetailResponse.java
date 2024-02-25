package coffee.ssafy.ssafee.domain.room.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RoomDetailResponse(
        String id,
        String name,
        Integer generation,
        Integer classroom,
        Instant lastOrderTime,
        Instant createdAt,
        Long shopId,
        Long userId,
        CreatorResponse creator
) {
}
