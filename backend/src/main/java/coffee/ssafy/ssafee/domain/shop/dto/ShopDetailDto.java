package coffee.ssafy.ssafee.domain.shop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ShopDetailDto {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String image;
    private Boolean enabledAutoOrder;
    private List<MenuCategoryDetailDto> menuCategories;

}
