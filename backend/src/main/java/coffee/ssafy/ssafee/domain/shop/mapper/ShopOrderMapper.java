package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopOrderMapper {

    OrderMenuResponse toDto(Party party);

}
