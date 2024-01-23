package coffee.ssafy.ssafee.domain.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionDetailDto {

    private Long id;
    private String name;
    private Integer price;

}
