package coffee.ssafy.ssafee.domain.room.mapper;

import coffee.ssafy.ssafee.domain.room.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.room.entity.Participant;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ParticipantMapper {

    ParticipantResponse toResponse(Participant participant);

}
