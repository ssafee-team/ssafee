package coffee.ssafy.ssafee.domain.order.controller;

import coffee.ssafy.ssafee.domain.order.dto.response.OrderDetailResponse;
import coffee.ssafy.ssafee.domain.order.dto.response.OrderResponse;
import coffee.ssafy.ssafee.domain.order.service.RoomOrderService;
import coffee.ssafy.ssafee.domain.order.service.ShopOrderService;
import coffee.ssafy.ssafee.domain.shop.service.ShopService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/shops/{shopId}/orders")
@RequiredArgsConstructor
public class ShopOrderController {

    private final RoomOrderService roomOrderService;
    private final ShopOrderService shopOrderService;
    private final ShopService shopService;
    private final SimpMessagingTemplate messagingTemplate;

    // 사장이 내 가게의 과거 주문내역을 다 뽑는거.
    @GetMapping
    @Operation(summary = "매니저: 주문기록조회", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<List<OrderDetailResponse>> readAll(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                             @PathVariable Long shopId) {
        shopService.validatePrincipal(principal, shopId);
        return ResponseEntity.ok().body(shopOrderService.findAll(shopId));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "매니저 : 주문확인", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> confirm(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                        @PathVariable Long shopId,
                                        @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        String roomId = shopOrderService.confirm(shopId, id);
        sendStatus(roomId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "매니저 : 주문거절", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> reject(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        String roomId = shopOrderService.reject(shopId, id);
        sendStatus(roomId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/made")
    @Operation(summary = "매니저 : 제조완료", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> make(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                     @PathVariable Long shopId,
                                     @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        String roomId = shopOrderService.make(shopId, id);
        sendStatus(roomId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deliver")
    @Operation(summary = "매니저 : 배달시작", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> deliver(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                        @PathVariable Long shopId,
                                        @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        String roomId = shopOrderService.deliver(shopId, id);
        sendStatus(roomId);
        return ResponseEntity.noContent().build();
    }

    private void sendStatus(String roomId) {
        OrderResponse orderResponse = roomOrderService.find(roomId);
        messagingTemplate.convertAndSend("/sub/rooms/" + roomId + "/status", orderResponse);
    }

}
