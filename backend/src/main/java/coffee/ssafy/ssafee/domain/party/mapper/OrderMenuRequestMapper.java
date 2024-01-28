package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.common.RequestMapper;
import coffee.ssafy.ssafee.domain.party.dto.request.OrderMenuRequest;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenuOption;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMenuRequestMapper extends RequestMapper<OrderMenuRequest, OrderMenu> {

    @Mapping(target = "orderMenuOptionCategories", source = "optionCategories")
    @Mapping(target = "orderMenuOptionCategories.orderMenuOptions", expression = "java(toOrderMenuOptions(orderMenuRequest.getOptionCategories()))")
    OrderMenu toEntity(OrderMenuRequest orderMenuRequest);

    List<OrderMenuOption> toOrderMenuOptions(List<OptionCategory> OptionCategories);

}
