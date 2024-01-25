package coffee.ssafy.ssafee.domain.party.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyRequest(
        String name,
        Integer generation,
        Integer classroom,
        LocalDateTime lastOrderTime,
        Long shopId,
        CreatorRequest creator
) {
}
