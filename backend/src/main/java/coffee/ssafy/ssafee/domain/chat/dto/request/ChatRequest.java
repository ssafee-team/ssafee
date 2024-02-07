package coffee.ssafy.ssafee.domain.chat.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatRequest(
        String name,
        String content
) {
}
