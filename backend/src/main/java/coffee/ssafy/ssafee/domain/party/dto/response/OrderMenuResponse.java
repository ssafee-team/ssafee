package coffee.ssafy.ssafee.domain.party.dto.response;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderMenuResponse {

    private Long id;
    private MenuResponse menu;

}
