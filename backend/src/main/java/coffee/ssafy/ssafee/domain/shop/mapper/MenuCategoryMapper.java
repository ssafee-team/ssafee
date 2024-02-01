package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryName;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {

    // <반환타입> 함수이름(<입력타입> 입력이름)
    //    response toDto(entity shop);
    //    entity toEntity(request menuCategory);
    //    @Mapping(target = [반환타입], source = [])


    MenuCategory toEntity(MenuCategoryRequest menuCategoryRequest);

    MenuCategoryResponse toDto(MenuCategory menuCategory);

    // MenuCategoryName 타입을 String으로 변환하는 매핑 메소드
    default String map(MenuCategoryName value) {
        return value.name();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "shop", ignore = true)
    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "name", source = "menuCategoryRequest.name")
    void updateFromDto(MenuCategoryRequest menuCategoryRequest, @MappingTarget MenuCategory menuCategory);

    @AfterMapping
    default void updateMenuCategory(@MappingTarget Menu menu, MenuRequest menuRequest) {
        menu.updateMenu(menuRequest, menu);
    }

}
