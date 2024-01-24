package coffee.ssafy.ssafee.domain.party.dto.response;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionDetailResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderMenuDetailResponse {

    private Long id;
    private MenuResponse menu;
    private List<OptionDetailResponse> options;

}
