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

    private final PartyService partyService;
    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    public List<ParticipantResponse> findParticipantsByAccessCode(String accessCode) {
        Long partyId = partyService.findPartyIdByAccessCode(accessCode);
        return participantRepository.findByPartyId(partyId).stream()
                .map(participantMapper::toDto)
                .toList();
    }

    public void updateParticipantByAccessCodeAndId(String accessCode, Long id, ParticipantUpdateRequest participantUpdateRequest) {
        Long partyId = partyService.findPartyIdByAccessCode(accessCode);
        participantRepository.findByPartyIdAndId(partyId, id)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTICIPANT))
                .update(participantUpdateRequest);
    }

}
