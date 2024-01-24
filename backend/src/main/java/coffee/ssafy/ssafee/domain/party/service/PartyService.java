package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.*;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.PartyMapper;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final EntityManager entityManager;
    private final PartyMapper partyMapper;

    private String generateRandomString(int length) {
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

    public String createParty(PartyReqDto partyReqDto) {
        String accessCode = generateRandomString(10);
        Shop shopReference = entityManager.getReference(Shop.class, partyReqDto.getShopId());
        Party party = Party.builder()
                .name(partyReqDto.getName())
                .generation(partyReqDto.getGeneration())
                .classroom(partyReqDto.getClassroom())
                .lastOrderTime(partyReqDto.getLastOrderTime())
                .accessCode(accessCode)
                .shop(shopReference)
                .creator(partyMapper.INSTANCE.toEntity(partyReqDto.getCreator()))
                .build();
        party.getCreator().setParty(party);
        partyRepository.save(party);
        return accessCode;
    }

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
