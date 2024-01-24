package coffee.ssafy.ssafee.domain.party.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class CreatorReqDto {

    private String name;
    private String email;
    private String bank;
    private String account;

}
