package coffee.ssafy.ssafee.domain.shop.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopResponse {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String image;
    private Boolean enabledAutoOrder;

}
