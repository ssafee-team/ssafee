package coffee.ssafy.ssafee.domain.party.dto.response;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionDetailResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderMenuDetailResponse(
        Long id,
        MenuResponse menu,
        List<OptionDetailResponse> options
) {
}