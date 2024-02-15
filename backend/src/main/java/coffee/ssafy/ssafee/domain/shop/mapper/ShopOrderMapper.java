package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.mapper.ChoiceMenuMapper;
import coffee.ssafy.ssafee.domain.shop.dto.response.PartyDetailForManagerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChoiceMenuMapper.class})
public interface ShopOrderMapper {

    @Mapping(target = "partyId", source = "id")
    @Mapping(target = "partyName", source = "name")
    @Mapping(target = "partyStatus", source = "party")
    PartyDetailForManagerResponse toDetailResponse(Party party);

}
