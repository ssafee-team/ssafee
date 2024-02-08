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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class PartyService {

    private static final int ACCESS_CODE_LENGTH = 10;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;

    public String createParty(Long userId, PartyRequest partyRequest) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String accessCode = ThreadLocalRandom.current()
                .ints(ACCESS_CODE_LENGTH, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        User userReference = entityManager.getReference(User.class, userId);
        Shop shopReference = entityManager.getReference(Shop.class, partyRequest.shopId());

        Party party = partyMapper.toEntity(partyRequest);
        party.prepareCreation(accessCode, shopReference, userReference, partyRequest.creator());
        partyRepository.save(party);
        return accessCode;
    }

    public List<PartyResponse> findParties(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            startDate = endDate = LocalDate.now();
        }
        return partyRepository.findAllByCreatedTimeBetween(startDate, endDate).stream()
                .map(partyMapper::toDto)
                .toList();
    }

    public PartyDetailResponse findParty(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .map(partyMapper::toDetailDto)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY));
    }

    public Long findPartyId(String accessCode) {
        return partyRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_EXISTS_PARTY))
                .getId();
    }

    public void validateUser(String accessCode, Long userId) {
        if (!partyRepository.existsByAccessCodeAndUserId(accessCode, userId)) {
            throw new PartyException(PartyErrorCode.UNAUTHORIZED_USER);
        }
    }

}
