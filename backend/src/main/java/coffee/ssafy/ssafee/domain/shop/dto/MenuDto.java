package coffee.ssafy.ssafee.domain.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuDto {

    private Long id;
    private String name;
    private String price;
    private String image;

}
