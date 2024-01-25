package coffee.ssafy.ssafee.domain.party.dto.response;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderMenuResponse(
        Long id,
        MenuResponse menu
) {
}

