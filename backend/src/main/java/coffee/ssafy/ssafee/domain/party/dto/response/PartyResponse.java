package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PartyResponse {

    private Long id;
    private String name;
    private Integer generation;
    private Integer classroom;
    private LocalDateTime lastOrderTime;
    private LocalDateTime createdTime;

}
