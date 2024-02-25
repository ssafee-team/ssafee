package coffee.ssafy.ssafee.domain.chat.dto;

import lombok.Builder;

@Builder
public record ChatInfo(
        String name,
        String content
) {
}
