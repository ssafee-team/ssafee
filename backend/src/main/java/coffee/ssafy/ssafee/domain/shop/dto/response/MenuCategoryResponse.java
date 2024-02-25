package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

@Builder
public record MenuCategoryResponse(
        Long id,
        String name
) {
}
