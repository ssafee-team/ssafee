package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.common.ResponseMapper;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartyResponseMapper extends ResponseMapper<PartyResponse, Party> {

    @Mapping(source = "shop.id", target = "shopId")
    PartyResponse toDto(Party party);

    @Mapping(source = "shop.id", target = "shopId")
    PartyDetailResponse toDetailDto(Party party);

}
