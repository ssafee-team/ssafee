package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.common.ResponseMapper;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.party.entity.Participant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantResponseMapper extends ResponseMapper<ParticipantResponse, Participant> {
}
