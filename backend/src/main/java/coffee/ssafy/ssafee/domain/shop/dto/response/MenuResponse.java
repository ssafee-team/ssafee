package coffee.ssafy.ssafee.domain.shop.dto.response;

import lombok.Builder;

@Builder
public record MenuResponse(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        Boolean soldOut
) {
}
