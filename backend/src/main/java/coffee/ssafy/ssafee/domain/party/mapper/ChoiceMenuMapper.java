package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenuOption;
import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenuOptionCategory;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChoiceMenuMapper {

    @Mapping(target = "participantName", source = "participant.name")
    @Mapping(target = "optionCategories", source = "choiceMenuOptionCategories")
    ChoiceMenuResponse toResponse(ChoiceMenu choiceMenu);

    @Mapping(target = "id", source = "optionCategory.id")
    @Mapping(target = "name", source = "optionCategory.name")
    @Mapping(target = "options", source = "choiceMenuOptions")
    @Mapping(target = "required", source = "optionCategory.required")
    @Mapping(target = "maxCount", source = "optionCategory.maxCount")
    OptionCategoryDetailResponse map(ChoiceMenuOptionCategory choiceMenuOptionCategory);

    @Mapping(target = "id", source = "option.id")
    @Mapping(target = "name", source = "option.name")
    @Mapping(target = "price", source = "option.price")
    OptionResponse map(ChoiceMenuOption choiceMenuOption);

}
