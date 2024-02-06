package coffee.ssafy.ssafee.domain.shop.dto.response;

import coffee.ssafy.ssafee.domain.party.dto.response.OrderResponse;
import coffee.ssafy.ssafee.domain.party.entity.Order;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyInfoForManagerResponse(
        Long id,
        String Name,
        Long shopId,
        Long orderId
) {
}
