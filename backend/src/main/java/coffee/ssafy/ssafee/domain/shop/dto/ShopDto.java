package coffee.ssafy.ssafee.domain.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopDto {

    private String name;
    private String address;
    private String phone;
    private String image;

}
