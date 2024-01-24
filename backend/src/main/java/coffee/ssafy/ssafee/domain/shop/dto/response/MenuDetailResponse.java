package coffee.ssafy.ssafee.domain.shop.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuDetailResponse {

    private Long id;
    private String name;
    private Integer price;
    private String image;
    private List<OptionCategoryDetailResponse> optionCategories;

}
