package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionCategoryMapper {

    OptionCategoryResponse toResponse(OptionCategory optionCategory);

    OptionCategoryDetailResponse toDetailResponse(OptionCategory optionCategory);

}
