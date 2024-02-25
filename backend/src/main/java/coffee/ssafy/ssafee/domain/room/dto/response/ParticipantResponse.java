package coffee.ssafy.ssafee.domain.room.dto.response;

import lombok.Builder;

@Builder
public record ParticipantResponse(
        Long id,
        String name,
        Boolean pickedCarrier,
        Boolean paid
) {
}
