package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.request.ChoiceMenuRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.service.ChoiceMenuService;
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

    private final ChoiceMenuService choiceMenuService;

    @PostMapping
    @Operation(summary = "주문 메뉴 생성")
    public ResponseEntity<Void> create(@PathVariable("access_code") String accessCode, @RequestBody ChoiceMenuRequest choiceMenuRequest) {
        Long id = choiceMenuService.create(accessCode, choiceMenuRequest);
        URI location = URI.create("/api/v1/parties/" + accessCode + "/order-menus/" + id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "주문 메뉴 목록")
    public ResponseEntity<List<ChoiceMenuResponse>> getList(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(choiceMenuService.findOrderMenusByAccessCode(accessCode));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "주문 메뉴 삭제")
    public ResponseEntity<Void> delete(@PathVariable("access_code") String accessCode, @PathVariable Long id) {
        choiceMenuService.deleteOrderMenuByAccessCodeAndId(accessCode, id);
        return ResponseEntity.noContent().build();
    }

}
