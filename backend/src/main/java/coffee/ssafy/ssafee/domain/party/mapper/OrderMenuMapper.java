package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.domain.party.dto.response.ChosenOptionCategoryResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ChosenOptionResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenuOption;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenuOptionCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMenuMapper {

    @Mapping(target = "id", source = "orderMenu.id")
    @Mapping(target = "participantName", source = "participant.name")
    @Mapping(target = "chosenMenu", source = "menu")
    @Mapping(target = "chosenOptionCategories", source = "orderMenuOptionCategories")
    OrderMenuResponse toDto(OrderMenu orderMenu);

    @Mapping(target = "id", source = "optionCategory.id")
    @Mapping(target = "name", source = "optionCategory.name")
    @Mapping(target = "chosenOptions", source = "orderMenuOptions")
    ChosenOptionCategoryResponse map(OrderMenuOptionCategory orderMenuOptionCategory);

    @Mapping(target = "id", source = "option.id")
    @Mapping(target = "name", source = "option.name")
    @Mapping(target = "price", source = "option.price")
    ChosenOptionResponse map(OrderMenuOption orderMenuOption);

}
