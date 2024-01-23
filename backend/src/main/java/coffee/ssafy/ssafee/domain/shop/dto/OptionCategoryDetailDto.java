package coffee.ssafy.ssafee.domain.shop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OptionCategoryDetailDto {

    private Long id;
    private String name;
    private Boolean required;
    private Integer maxCount;
    private List<OptionDetailDto> options;

}
