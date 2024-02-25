package coffee.ssafy.ssafee.domain.room.dto.response;

import lombok.Builder;

@Builder
public record CreatorResponse(
        Long id,
        String name,
        String bank,
        String account
) {
}
