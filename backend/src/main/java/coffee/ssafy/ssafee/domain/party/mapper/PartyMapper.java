package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.domain.party.dto.*;
import coffee.ssafy.ssafee.domain.party.entity.Creator;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import coffee.ssafy.ssafee.domain.party.entity.Participant;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartyMapper {

    PartyMapper INSTANCE = Mappers.getMapper(PartyMapper.class);

    PartyDto toDto(Party shop);

    ParticipantDetailDto toDetailDto(Participant menu);

    PartyDetailDto toDetailDto(Party shop);

    OrderMenuDetailDto toDetailDto(OrderMenu menu);

    Creator toEntity(CreatorReqDto creatorReqDto);

}
