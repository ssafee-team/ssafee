package coffee.ssafy.ssafee.domain.shop.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopDetailDto {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String image;
    private Boolean enabledAutoOrder;
    private List<MenuCategoryDetailDto> menuCategories;

}
