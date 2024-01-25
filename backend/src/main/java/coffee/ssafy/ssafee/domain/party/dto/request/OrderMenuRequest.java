package coffee.ssafy.ssafee.domain.party.dto.request;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderMenuRequest(
        MenuResponse menu,
        List<Long> optionIds
) {
}
