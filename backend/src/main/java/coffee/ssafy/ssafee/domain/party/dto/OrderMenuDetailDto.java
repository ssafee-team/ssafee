package coffee.ssafy.ssafee.domain.party.dto;

import coffee.ssafy.ssafee.domain.shop.dto.MenuDto;
import coffee.ssafy.ssafee.domain.shop.dto.OptionDetailDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderMenuDetailDto {

    private Long id;
    private MenuDto menu;
    private List<OptionDetailDto> options;

}
