package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.domain.party.dto.PartyStatusInfo;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyStatusResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartyOrderMapper {

    PartyStatusInfo toInfo(Party party);

    PartyStatusResponse toResponse(Party party);

    PartyStatusResponse toResponse(PartyStatusInfo partyStatusInfo);

}
