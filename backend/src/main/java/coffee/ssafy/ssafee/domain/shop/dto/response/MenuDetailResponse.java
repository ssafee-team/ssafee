package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MenuDetailResponse(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        Boolean soldOut,
        List<OptionCategoryDetailResponse> optionCategories
) {
}
