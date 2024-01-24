package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.*;
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
    public ResponseEntity<Void> createParty(@RequestBody PartyReqDto partyReqDto) {
        String accessCode = partyService.createParty(partyReqDto);
        URI location = URI.create("/p/" + accessCode);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<PartyDto>> getPartiesToday() {
        return ResponseEntity.ok().body(partyService.findPartiesToday());
    }

    @GetMapping("/{access_code}")
    public ResponseEntity<PartyDetailDto> getParty(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(partyService.findPartyByAccessCode(accessCode));
    }

    @GetMapping("/{access_code}/order-menus")
    public ResponseEntity<List<ParticipantDetailDto>> getOrderMenus(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(partyService.findOrderMenusByAccessCode(accessCode));
    }

    @GetMapping("/{access_code}/order-menus/{id}")
    public ResponseEntity<OrderMenuDetailDto> getOrderMenu(@PathVariable("access_code") String accessCode, @PathVariable Long id) {
        return ResponseEntity.ok().body(partyService.findOrderMenuByAccessCodeAndId(accessCode, id));
    }

}
