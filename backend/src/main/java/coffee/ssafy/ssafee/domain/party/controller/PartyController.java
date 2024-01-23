package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.OrderMenuDetailDto;
import coffee.ssafy.ssafee.domain.party.dto.ParticipantDetailDto;
import coffee.ssafy.ssafee.domain.party.dto.PartyDetailDto;
import coffee.ssafy.ssafee.domain.party.dto.PartyDto;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

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
