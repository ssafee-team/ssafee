package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record OptionCategoryDetailResponse(
        Long id,
        String name,
        Boolean required,
        Integer maxCount,
        List<OptionResponse> options
) {
}
