package coffee.ssafy.ssafee.domain.room.dto.request;

import java.util.List;

public record ChoiceMenuCreateRequest(
        String participantName,
        Long menuId,
        List<ChoiceOptionCategoryRequest> optionCategories
) {
}
