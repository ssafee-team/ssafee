package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChosenOptionCategoryResponse(
        Long id,
        String name,
        List<ChosenOptionResponse> options
) {
}
