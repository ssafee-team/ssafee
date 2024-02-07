package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyStatusResponse;
import coffee.ssafy.ssafee.domain.party.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.party.service.PartyOrderService;
import coffee.ssafy.ssafee.domain.party.service.PartySocketIoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/api/v1/parties/{access_code}")
@RequiredArgsConstructor
public class PartyOrderController {

    private final PartySocketIoService partySocketIoService;
    private final PartyOrderService partyOrderService;

    @PostMapping("/order")
    @Operation(summary = "총무 : 주문 요청 생성")
    public ResponseEntity<Void> createOrder(@PathVariable("access_code") String accessCode) {
        // 총무가 "주문요청" 버튼 클릭
        // 사장님 화면에 주문요청 생성 (알림 발송) (수락/거절)
        Long partyId = partyOrderService.createOrder(accessCode);
        partySocketIoService.sendOrderNotification(partyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order")
    @Operation(summary = "총무 : 주문현황조회")
    public ResponseEntity<PartyStatusResponse> getOrders(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(partyOrderService.getOrders(accessCode));
    }

    @PostMapping("/notice")
    @Operation(summary = "총무 : 알림보내기")
    public ResponseEntity<Void> orderDelivered(@PathVariable("access_code") String accessCode) {
        partyOrderService.orderDelivered(accessCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/give-me-money")
    @Operation(summary = "총무 : 송금요청 알림보내기")
    public ResponseEntity<Void> giveMeMoney(@PathVariable("access_code") String accessCode) {
        partyOrderService.giveMeMoney(accessCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ad")
    @Operation(summary = "총무 : 커피파티 홍보알림 보내기")
    public ResponseEntity<Void> sendAdvertise(@PathVariable("access_code") String accessCode) {
        partyOrderService.sendAdvertise(accessCode);
        return ResponseEntity.ok().build();
    }

}
