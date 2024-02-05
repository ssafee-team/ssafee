package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.service.PartyOrderService;
import coffee.ssafy.ssafee.domain.party.service.PartySocketIoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parties")
@RequiredArgsConstructor
public class PartyOrderController {

    private final PartySocketIoService partySocketIoService;
    private final PartyOrderService partyOrderService;

    @PostMapping("/{access_code}/order")
    @Operation(summary = "주문 요청 생성")
    public ResponseEntity<Void> createOrder(@PathVariable("access_code") String accessCode) {
        // 총무가 "주문요청" 버튼 클릭
        // 사장님 화면에 주문요청 생성 (알림 발송) (수락/거절)
        Long partyId = partyOrderService.createOrder(accessCode);
        partySocketIoService.sendOrderNotification(partyId);
        return ResponseEntity.ok().build();
    }

}
