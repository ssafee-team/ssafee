package coffee.ssafy.ssafee.domain.order.controller;

import coffee.ssafy.ssafee.domain.order.dto.OrderInfo;
import coffee.ssafy.ssafee.domain.order.dto.response.OrderResponse;
import coffee.ssafy.ssafee.domain.order.mapper.OrderMapper;
import coffee.ssafy.ssafee.domain.order.service.RoomOrderService;
import coffee.ssafy.ssafee.domain.room.service.RoomService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rooms/{roomId}/order")
@RequiredArgsConstructor
public class RoomOrderController {

    private final RoomService roomService;
    private final RoomOrderService roomOrderService;
    private final OrderMapper orderMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    @Operation(summary = "총무 : 주문 요청 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> create(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable String roomId) {
        roomService.validatePrincipal(roomId, principal.id());
        OrderInfo orderInfo = roomOrderService.create(roomId);
        roomOrderService.pickCarriers(roomId);
        messagingTemplate.convertAndSend("/sub/shops/" + orderInfo.shopId() + "/orders", orderMapper.toResponse(orderInfo));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "총무 : 주문 현황 조회")
    public ResponseEntity<OrderResponse> read(@PathVariable String roomId) {
        return ResponseEntity.ok().body(roomOrderService.find(roomId));
    }

}
