package coffee.ssafy.ssafee.domain.room.dto.response;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryDetailResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ChoiceMenuResponse(
        Long id,
        Long participantId,
        String participantName,
        MenuResponse menu,
        List<OptionCategoryDetailResponse> optionCategories
) {
}
