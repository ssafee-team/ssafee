package coffee.ssafy.ssafee.domain.shop.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OptionCategoryDetailResponse(
        Long id,
        String name,
        Boolean required,
        Integer maxCount,
        List<OptionDetailResponse> options
) {
}
