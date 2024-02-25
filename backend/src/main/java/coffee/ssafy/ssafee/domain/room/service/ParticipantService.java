package coffee.ssafy.ssafee.domain.room.service;

import coffee.ssafy.ssafee.domain.room.dto.request.ParticipantUpdateRequest;
import coffee.ssafy.ssafee.domain.room.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.room.exception.RoomErrorCode;
import coffee.ssafy.ssafee.domain.room.exception.RoomException;
import coffee.ssafy.ssafee.domain.room.mapper.ParticipantMapper;
import coffee.ssafy.ssafee.domain.room.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @Transactional(readOnly = true)
    public List<ParticipantResponse> findAll(String roomId) {
        return participantRepository.findAllByRoomId(roomId).stream()
                .map(participantMapper::toResponse)
                .toList();
    }

    public void update(String roomId, Long id, ParticipantUpdateRequest participantUpdateRequest) {
        participantRepository.findByRoomIdAndId(roomId, id)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_PARTICIPANT))
                .updatePaid(participantUpdateRequest);
    }

}
