package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailForManagerResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.mapper.ChoiceMenuMapper;
import coffee.ssafy.ssafee.domain.shop.dto.response.PartyInfoForManagerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChoiceMenuMapper.class})
public interface ShopOrderMapper {

    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "partyId", source = "id")
    PartyInfoForManagerResponse toResponse(Party party);

    @Mapping(target = "partyId", source = "id")
    PartyDetailForManagerResponse toDetailResponse(Party party);

}
