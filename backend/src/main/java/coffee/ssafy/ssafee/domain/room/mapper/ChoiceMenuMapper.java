package coffee.ssafy.ssafee.domain.room.mapper;

import coffee.ssafy.ssafee.domain.room.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceOption;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceOptionCategory;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ChoiceMenuMapper {

    @Mapping(target = "participantId", source = "participant.id")
    @Mapping(target = "participantName", source = "participant.name")
    @Mapping(target = "optionCategories", source = "choiceOptionCategories")
    ChoiceMenuResponse toResponse(ChoiceMenu choiceMenu);

    @Mapping(target = "id", source = "optionCategory.id")
    @Mapping(target = "name", source = "optionCategory.name")
    @Mapping(target = "options", source = "choiceOptions")
    @Mapping(target = "required", source = "optionCategory.required")
    @Mapping(target = "maxCount", source = "optionCategory.maxCount")
    OptionCategoryDetailResponse toResponse(ChoiceOptionCategory choiceOptionCategory);

    @Mapping(target = "id", source = "option.id")
    @Mapping(target = "name", source = "option.name")
    @Mapping(target = "price", source = "option.price")
    OptionResponse toResponse(ChoiceOption choiceOption);

}
