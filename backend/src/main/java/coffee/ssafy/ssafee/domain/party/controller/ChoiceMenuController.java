package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.request.ChoiceMenuCreateRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.service.ChoiceMenuService;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parties/{access_code}/order-menus")
@RequiredArgsConstructor
public class ChoiceMenuController {

    private final PartyService partyService;
    private final ChoiceMenuService choiceMenuService;

    @PostMapping
    @Operation(summary = "주문 메뉴 생성")
    public ResponseEntity<Void> createChoiceMenu(@PathVariable("access_code") String accessCode,
                                                 @RequestBody ChoiceMenuCreateRequest choiceMenuCreateRequest) {
        Long id = choiceMenuService.createChoiceMenu(accessCode, choiceMenuCreateRequest);
        URI location = URI.create("/api/v1/parties/" + accessCode + "/order-menus/" + id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "주문 메뉴 목록")
    public ResponseEntity<List<ChoiceMenuResponse>> getChoiceMenus(@PathVariable("access_code") String accessCode) {
        Long partyId = partyService.findPartyId(accessCode);
        return ResponseEntity.ok().body(choiceMenuService.findChoiceMenus(partyId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "주문 메뉴 삭제")
    public ResponseEntity<Void> deleteChoiceMenu(@PathVariable("access_code") String accessCode,
                                                 @PathVariable("id") Long orderMenuId) {
        Long partyId = partyService.findPartyId(accessCode);
        choiceMenuService.deleteChoiceMenu(partyId, orderMenuId);
        return ResponseEntity.noContent().build();
    }

}
