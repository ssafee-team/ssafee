package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.domain.party.dto.request.PartyRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartyMapper {

    @Mapping(source = "shop.id", target = "shopId")
    PartyResponse toDto(Party party);

    @Mapping(source = "shop.id", target = "shopId")
    PartyDetailResponse toDetailDto(Party party);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Party toEntity(PartyRequest partyRequest);

}
