package coffee.ssafy.ssafee.domain.party.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParticipantDetailDto {

    private Long id;
    private String name;
    private Boolean isCreator;
    private Boolean isCarrier;
    private Boolean isPayed;
    private Boolean isPayConfirmed;
    private List<OrderMenuDto> orderMenus;

}
