package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.common.RequestMapper;
import coffee.ssafy.ssafee.domain.party.dto.request.PartyRequest;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartyRequestMapper extends RequestMapper<PartyRequest, Party> {

    @Mapping(target = "creator", ignore = true)
    Party toEntity(PartyRequest partyRequest);

}
