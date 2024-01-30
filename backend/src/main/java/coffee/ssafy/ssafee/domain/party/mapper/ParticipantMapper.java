package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.domain.party.dto.response.ChosenOptionCategoryResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ChosenOptionResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenuOption;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenuOptionCategory;
import coffee.ssafy.ssafee.domain.party.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    List<ParticipantResponse> toDtoList(List<Participant> participants);

    @Mapping(target = "optionCategories", source = "orderMenuOptionCategories")
    OrderMenuResponse map(OrderMenu orderMenu);

    @Mapping(target = "id", source = "optionCategory.id")
    @Mapping(target = "name", source = "optionCategory.name")
    @Mapping(target = "options", source = "orderMenuOptions")
    ChosenOptionCategoryResponse map(OrderMenuOptionCategory orderMenuOptionCategory);

    @Mapping(target = "id", source = "option.id")
    @Mapping(target = "name", source = "option.name")
    @Mapping(target = "price", source = "option.price")
    ChosenOptionResponse map(OrderMenuOption orderMenuOption);

}
