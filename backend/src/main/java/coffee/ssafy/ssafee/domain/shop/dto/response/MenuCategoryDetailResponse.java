package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MenuCategoryDetailResponse(
        Long id,
        String name,
        List<MenuDetailResponse> menus
) {
}
