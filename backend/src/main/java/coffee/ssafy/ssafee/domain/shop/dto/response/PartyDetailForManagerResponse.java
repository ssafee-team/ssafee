package coffee.ssafy.ssafee.domain.shop.dto.response;

import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyStatusResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyDetailForManagerResponse(
        Long partyId,
        String partyName,
        PartyStatusResponse partyStatus,
        List<ChoiceMenuResponse> choiceMenus
) {
}
