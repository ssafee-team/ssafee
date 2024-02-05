package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {

    MenuCategoryResponse toDto(MenuCategory menuCategory);

    MenuCategory toEntity(MenuCategoryRequest menuCategoryRequest);

    @AfterMapping
    default void update(@MappingTarget MenuCategory menuCategory, MenuCategoryRequest menuCategoryRequest) {
        menuCategory.update(menuCategoryRequest);
    }

}
