package coffee.ssafy.ssafee.domain.shop.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ShopDetailResponse(
        Long id,
        String name,
        String address,
        String phone,
        String image,
        Boolean enabledAutoOrder,
        List<MenuCategoryResponse> menuCategories
) {
}
