package coffee.ssafy.ssafee.domain.party.dto.response;

import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyDetailForManagerResponse(
        Long partyId,
        String partyName,
        Long shopId,
        LocalDateTime realOrderTime,
        LocalDateTime deliveredTime,
        String creatorName,
        List<ChoiceMenu> choiceMenus
) {

}
