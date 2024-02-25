package coffee.ssafy.ssafee.domain.order.mapper;

import coffee.ssafy.ssafee.domain.order.dto.OrderInfo;
import coffee.ssafy.ssafee.domain.order.dto.response.OrderDetailResponse;
import coffee.ssafy.ssafee.domain.order.dto.response.OrderResponse;
import coffee.ssafy.ssafee.domain.order.entity.Order;
import coffee.ssafy.ssafee.domain.room.mapper.ChoiceMenuMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR, uses = ChoiceMenuMapper.class)
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    OrderResponse toResponse(OrderInfo orderInfo);

    @Mapping(target = "choiceMenus", source = "room.choiceMenus")
    OrderDetailResponse toDetailResponse(Order order);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "shopId", source = "shop.id")
    OrderInfo toInfo(Order order);

}
