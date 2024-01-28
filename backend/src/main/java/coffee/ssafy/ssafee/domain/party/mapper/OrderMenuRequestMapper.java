package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.common.RequestMapper;
import coffee.ssafy.ssafee.domain.party.dto.request.OrderMenuRequest;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMenuRequestMapper extends RequestMapper<OrderMenuRequest, OrderMenu> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderMenuOptionCategories", source = "optionCategories")
    @Mapping(target = "orderMenuOptionCategories.orderMenuOptions", expression = "java(toOrderMenuOptions(orderMenuRequest.getOptionCategories()))")
    OrderMenu toEntity(OrderMenuRequest orderMenuRequest);

}
