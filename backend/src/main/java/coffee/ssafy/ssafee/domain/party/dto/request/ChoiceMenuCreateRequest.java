package coffee.ssafy.ssafee.domain.party.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChoiceMenuCreateRequest(
        Long menuId,
        String participantName,
        List<ChoiceOptionCategoryRequest> optionCategories
) {
}
