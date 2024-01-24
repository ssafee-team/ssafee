package coffee.ssafy.ssafee.domain.party.dto.request;

import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParticipantRequest {

    private String name;
    private Boolean isCreator;
    private Boolean isCarrier;
    private Boolean payed;
    private Boolean payConfirmed;
    private List<OrderMenuResponse> orderMenus;

}
