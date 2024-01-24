package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParticipantDetailResponse {

    private Long id;
    private String name;
    private Boolean isCreator;
    private Boolean isCarrier;
    private Boolean payed;
    private Boolean payConfirmed;
    private List<OrderMenuDetailResponse> orderMenus;

}
