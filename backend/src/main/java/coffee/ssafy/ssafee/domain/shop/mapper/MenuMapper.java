package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenusById;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuResponse toDto(Menu Menu);

    Menu toEntity(MenuRequest menuRequest);

    @AfterMapping
    default void updateMenu(@MappingTarget Menu menu, MenuRequest menuRequest) {
        menu.updateMenu(menuRequest, menu);
    }

}
