package coffee.ssafy.ssafee.domain.chat.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ChatResponse(
        Long id,
        String name,
        String content,
        Instant createdAt
) {
}
