package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.ParticipantUpdateRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.ParticipantMapper;
import coffee.ssafy.ssafee.domain.party.repository.ParticipantRepository;
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

    public List<ParticipantResponse> findParticipants(Long partyId) {
        return participantRepository.findAllByPartyId(partyId).stream()
                .map(participantMapper::toDto)
                .toList();
    }

    public void updateParticipant(Long partyId, Long id, ParticipantUpdateRequest participantUpdateRequest) {
        participantRepository.findByPartyIdAndId(partyId, id)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTICIPANT))
                .updatePaid(participantUpdateRequest);
    }

}
