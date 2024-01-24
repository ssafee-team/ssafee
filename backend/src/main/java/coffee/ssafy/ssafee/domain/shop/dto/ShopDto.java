package coffee.ssafy.ssafee.domain.shop.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopDto {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String image;
    private Boolean enabledAutoOrder;

}
