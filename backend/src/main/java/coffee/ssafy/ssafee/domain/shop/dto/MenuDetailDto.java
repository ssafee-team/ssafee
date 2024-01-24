package coffee.ssafy.ssafee.domain.shop.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuDetailDto {

    private Long id;
    private String name;
    private Integer price;
    private String image;
    private List<OptionCategoryDetailDto> optionCategories;

}
