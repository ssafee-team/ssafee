package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.ParticipantRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantDetailResponse;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.PartyMapper;
import coffee.ssafy.ssafee.domain.party.repository.OrderMenuRepository;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuService {

    private final PartyRepository partyRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PartyMapper partyMapper;

    public List<ParticipantDetailResponse> findOrderMenusByAccessCode(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY))
                .getParticipants().stream()
                .map(partyMapper.INSTANCE::toDetailDto)
                .toList();
    }

    public OrderMenuDetailResponse findOrderMenuByAccessCodeAndId(String accessCode, Long id) {
        return partyMapper.INSTANCE.toDetailDto(partyRepository.findOrderMenuByAccessCodeAndId(accessCode, id)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY_OR_ORDER_MENU)));
    }

    public void deleteOrderMenuByAccessCodeAndId(String accessCode, Long id) {
        partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
        orderMenuRepository.deleteById(id);
    }

    public String createOrderMenu(String accessCode, ParticipantRequest participantRequest) {
        partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));

        return "";
    }

}
