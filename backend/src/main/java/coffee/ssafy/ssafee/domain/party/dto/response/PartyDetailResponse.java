package coffee.ssafy.ssafee.domain.party.dto.response;

import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyDetailResponse(
        Long id,
        String name,
        Integer generation,
        Integer classroom,
        LocalDateTime lastOrderTime,
        LocalDateTime createdTime,
        ShopDetailResponse shop,
        CreatorResponse creator
) {
}
