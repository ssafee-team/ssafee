package coffee.ssafy.ssafee.domain.shop.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MenuDetailResponse(
        Long id,
        String name,
        Integer price,
        String image,
        List<OptionCategoryDetailResponse> optionCategories
) {
}