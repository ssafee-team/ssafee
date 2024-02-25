package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

@Builder
public record OptionResponse(
        Long id,
        String name,
        Integer price
) {
}
