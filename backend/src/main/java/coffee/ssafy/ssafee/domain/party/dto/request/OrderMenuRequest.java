package coffee.ssafy.ssafee.domain.party.dto.request;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderMenuRequest {

    private MenuResponse menu;
    private List<Long> optionIds;

}
