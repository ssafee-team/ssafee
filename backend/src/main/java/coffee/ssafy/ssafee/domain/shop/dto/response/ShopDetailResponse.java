package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ShopDetailResponse(
        Long id,
        String name,
        String address,
        String phone,
        String imageUrl,
        Boolean enabledOrder,
        Integer minimumPrice,
        Boolean closed,
        List<MenuCategoryDetailResponse> menuCategories
) {
}
