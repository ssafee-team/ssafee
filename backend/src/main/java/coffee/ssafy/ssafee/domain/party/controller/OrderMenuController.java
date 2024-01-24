package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantDetailResponse;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parties/{access_code}/order-menus")
@RequiredArgsConstructor
public class OrderMenuController {

    private final PartyService partyService;

    @GetMapping
    public ResponseEntity<List<ParticipantDetailResponse>> getOrderMenus(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(partyService.findOrderMenusByAccessCode(accessCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderMenuDetailResponse> getOrderMenu(@PathVariable("access_code") String accessCode, @PathVariable Long id) {
        return ResponseEntity.ok().body(partyService.findOrderMenuByAccessCodeAndId(accessCode, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderMenu(@PathVariable("access_code") String accessCode, @PathVariable Long id) {
        partyService.deleteOrderMenuByAccessCodeAndId(accessCode, id);
        return ResponseEntity.ok().build();
    }

}
