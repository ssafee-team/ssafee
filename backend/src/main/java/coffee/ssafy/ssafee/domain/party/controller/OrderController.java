package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.service.OrderService;
import coffee.ssafy.ssafee.domain.party.service.SocketIoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parties")
@RequiredArgsConstructor
public class OrderController {

    private final SocketIoService socketIoService;
    private final OrderService orderService;

    @PostMapping("/{access_code}/order")
    @Operation(summary = "주문 요청 생성")
    public ResponseEntity<Void> createOrder(@PathVariable("access_code") String accessCode) {
        // 총무가 "주문요청" 버튼 클릭
        // 사장님 화면에 주문요청 생성 (알림 발송) (수락/거절)
        Long partyId = orderService.createOrder(accessCode);
        socketIoService.sendOrderNotification(partyId);
        return ResponseEntity.ok().build();
    }

    //    @GetMapping("/{access_code}/order")
//    @Operation(summary = "주문 현황 조회")
//    public ResponseEntity<OrderMenuResponse> getOrderMenus(@PathVariable("access_code") String accessCode) {
//        return ResponseEntity.ok().body(partyService.getOrderMenus(accessCode));
//    }

}
