package coffee.ssafy.ssafee.domain.shop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MenuDetailDto {

    private Long id;
    private String name;
    private Integer price;
    private String image;
    private List<OptionCategoryDetailDto> optionCategories;

}
