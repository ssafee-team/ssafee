package coffee.ssafy.ssafee.domain.party.service;

import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

}
