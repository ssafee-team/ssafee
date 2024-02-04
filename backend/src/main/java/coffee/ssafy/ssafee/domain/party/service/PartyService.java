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
import coffee.ssafy.ssafee.jwt.JwtTokenProvider;
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

    private static final int ACCESS_CODE_LENGTH = 10;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;
    private final JwtTokenProvider jwtTokenProvider;

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

    public String createParty(String bearerToken, PartyRequest partyRequest) {
        String accessToken = jwtTokenProvider.bearerTokenToAccessToken(bearerToken);
        String userId = jwtTokenProvider.parseAccessToken(accessToken).id();
        User userReference = entityManager.getReference(User.class, userId);

        String accessCode = generateAccessCode();
        Shop shopReference = entityManager.getReference(Shop.class, partyRequest.shopId());

        Party party = partyMapper.toEntity(partyRequest);
        party.prepareCreation(accessCode, shopReference, userReference, partyRequest.creator());
        partyRepository.save(party);
        return accessCode;
    }

    public List<PartyResponse> findPartiesToday() {
        return partyRepository.findAllByCreatedTimeToday().stream()
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
