package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OptionCategoryMapper {

    OptionCategoryResponse toDto(OptionCategory optionCategory);

    OptionCategory toEntity(OptionCategoryRequest optionCategoryRequest);

    @AfterMapping
    default void update(@MappingTarget OptionCategory optionCategory, OptionCategoryRequest optionCategoryRequest) {
        optionCategory.update(optionCategoryRequest);
    }

}
