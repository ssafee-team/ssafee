package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.ParticipantRequest;
import coffee.ssafy.ssafee.domain.party.dto.request.PartyRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.PartyMapper;
import coffee.ssafy.ssafee.domain.party.repository.OrderMenuRepository;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class PartyService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyRepository partyRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PartyMapper partyMapper;

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public String createParty(PartyRequest partyRequest) {
        Party party = partyMapper.INSTANCE.toEntity(partyRequest);

        String accessCode = generateRandomString(10);
        Shop shopReference = entityManager.getReference(Shop.class, partyRequest.getShopId());
        party.updateCreator(accessCode, shopReference, partyRequest.getCreator());
        partyRepository.save(party);
        return accessCode;
    }

    public List<PartyResponse> findPartiesToday() {
        return partyRepository.findAll().stream()
                .map(partyMapper.INSTANCE::toDto)
                .toList();
    }

    public PartyDetailResponse findPartyByAccessCode(String accessCode) {
        return partyMapper.INSTANCE.toDetailDto(partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY)));
    }

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
