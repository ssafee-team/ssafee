package coffee.ssafy.ssafee.domain.shop.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MenuCategoryDetailResponse(
        Long id,
        String name,
        List<MenuResponse> menus
) {
}
