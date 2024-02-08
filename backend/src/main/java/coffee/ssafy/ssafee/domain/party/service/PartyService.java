package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.dto.request.PartyRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.exception.PartyErrorCode;
import coffee.ssafy.ssafee.domain.party.exception.PartyException;
import coffee.ssafy.ssafee.domain.party.mapper.PartyMapper;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class PartyService {

    private static final int ACCESS_CODE_LENGTH = 10;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;

    private static String generateAccessCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(ACCESS_CODE_LENGTH);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public String createParty(Long userId, PartyRequest partyRequest) {
        String accessCode = generateAccessCode();
        Shop shopReference = entityManager.getReference(Shop.class, partyRequest.shopId());
        User userReference = entityManager.getReference(User.class, userId);

        Party party = partyMapper.toEntity(partyRequest);
        party.prepareCreation(accessCode, shopReference, userReference, partyRequest.creator());
        partyRepository.save(party);
        return accessCode;
    }

    public List<PartyResponse> findParties(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (startDate == null || endDate == null) {
            startDate = today;
            endDate = today;
        }
        return partyRepository.findAllByCreatedTimeBetween(startDate, endDate).stream()
                .map(partyMapper::toDto)
                .toList();
    }

    public PartyDetailResponse findPartyByAccessCode(String accessCode) {
        return partyMapper.toDetailDto(partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY)));
    }

    public Long findPartyIdByAccessCode(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY))
                .getId();
    }

}
