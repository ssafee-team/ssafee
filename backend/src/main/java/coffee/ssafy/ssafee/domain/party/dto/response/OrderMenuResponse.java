package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderMenuResponse(
        Long id,
        String participantName,
        ChosenMenuResponse chosenMenu,
        List<ChosenOptionCategoryResponse> chosenOptionCategories
) {
}
