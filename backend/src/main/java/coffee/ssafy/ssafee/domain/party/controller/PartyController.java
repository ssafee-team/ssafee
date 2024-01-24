package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.request.PartyRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping
    public ResponseEntity<Void> createParty(@RequestBody PartyRequest partyRequest) {
        String accessCode = partyService.createParty(partyRequest);
        URI location = URI.create("/p/" + accessCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PartyResponse>> getPartiesToday() {
        return ResponseEntity.ok().body(partyService.findPartiesToday());
    }

    @GetMapping("/{access_code}")
    public ResponseEntity<PartyDetailResponse> getParty(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(partyService.findPartyByAccessCode(accessCode));
    }

}
