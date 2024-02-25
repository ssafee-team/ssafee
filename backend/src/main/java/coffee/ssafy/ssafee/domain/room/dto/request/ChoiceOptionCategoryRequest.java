package coffee.ssafy.ssafee.domain.room.dto.request;

import java.util.List;

public record ChoiceOptionCategoryRequest(
        Long optionCategoryId,
        List<Long> optionIds
) {
}
