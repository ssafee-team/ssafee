package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ShopOrderResponse(
        Long orderId,
        Boolean confirmed,
        Boolean rejected,
        Boolean maked,
        Boolean delivered,
        Long partyId
) {

}