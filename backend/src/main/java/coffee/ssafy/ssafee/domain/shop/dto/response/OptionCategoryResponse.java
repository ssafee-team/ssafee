package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

@Builder
public record OptionCategoryResponse(
        Long id,
        String name,
        Boolean required,
        Integer maxCount
) {
}
