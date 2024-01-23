package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.OrderMenuDetailDto;
import coffee.ssafy.ssafee.domain.party.dto.ParticipantDetailDto;
import coffee.ssafy.ssafee.domain.party.dto.PartyDetailDto;
import coffee.ssafy.ssafee.domain.party.dto.PartyDto;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.PartyMapper;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;

    public List<PartyDto> findPartiesToday() {
        return partyRepository.findAll().stream()
                .map(partyMapper.INSTANCE::toDto)
                .toList();
    }

    public PartyDetailDto findPartyByAccessCode(String accessCode) {
        return partyMapper.INSTANCE.toDetailDto(partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY)));
    }

    public List<ParticipantDetailDto> findOrderMenusByAccessCode(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY))
                .getParticipants().stream()
                .map(partyMapper.INSTANCE::toDetailDto)
                .toList();
    }

    public OrderMenuDetailDto findOrderMenuByAccessCodeAndId(String accessCode, Long id) {
        return partyMapper.INSTANCE.toDetailDto(partyRepository.findOrderMenuByAccessCodeAndId(accessCode, id)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY_OR_ORDER_MENU)));
    }

}
