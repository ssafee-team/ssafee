package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuResponse toDto(Menu Menu);

    Menu toEntity(MenuRequest menuRequest);

    @AfterMapping
    default void update(@MappingTarget Menu menu, MenuRequest menuRequest) {
        menu.update(menuRequest);
    }

}
