package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ParticipantResponse(
        Long id,
        String name,
        Boolean isCreator,
        Boolean isCarrier,
        Boolean payed,
        Boolean payConfirmed,
        List<OrderMenuResponse> orderMenus
) {
}
