package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MenuCategoryMapper {

    MenuCategoryResponse toResponse(MenuCategory menuCategory);

    MenuCategoryDetailResponse toDetailResponse(MenuCategory menuCategory);

}
