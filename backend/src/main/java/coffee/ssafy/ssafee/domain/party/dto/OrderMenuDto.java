package coffee.ssafy.ssafee.domain.party.dto;

import coffee.ssafy.ssafee.domain.shop.dto.MenuDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderMenuDto {

    private Long id;
    private MenuDto menu;

}
