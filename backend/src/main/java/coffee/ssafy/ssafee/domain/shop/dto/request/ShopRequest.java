package coffee.ssafy.ssafee.domain.shop.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ShopRequest(
        String name,
        String address,
        String phone,
        Boolean enabledOrder,
        Integer minimumPrice,
        Boolean closed
) {
}
