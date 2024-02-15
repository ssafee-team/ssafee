package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.party.dto.PartyStatusInfo;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyStatusResponse;
import coffee.ssafy.ssafee.domain.party.mapper.PartyOrderMapper;
import coffee.ssafy.ssafee.domain.party.service.PartyOrderService;
import coffee.ssafy.ssafee.domain.shop.dto.response.PartyDetailForManagerResponse;
import coffee.ssafy.ssafee.domain.shop.service.ShopOrderService;
import coffee.ssafy.ssafee.domain.user.service.ManagerService;
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
@RequestMapping("/api/v1/shops/{shop_id}/orders")
@RequiredArgsConstructor
public class ShopOrderController {

    private final PartyOrderService partyOrderService;
    private final ShopOrderService shopOrderService;
    private final ManagerService managerService;
    private final PartyOrderMapper partyOrderMapper;
    private final SimpMessagingTemplate messagingTemplate;

    // 사장이 내 가게의 과거 주문내역을 다 뽑는거.
    @GetMapping
    @Operation(summary = "매니저: 주문기록조회", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<List<PartyDetailForManagerResponse>> getShopHistory(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                                              @PathVariable("shop_id") Long shopId) {
        managerService.validateShop(principal, shopId);
        return ResponseEntity.ok().body(shopOrderService.getPartiesByShop(shopId));
    }

    @PostMapping("/{party_id}/confirm")
    @Operation(summary = "매니저 : 주문확인", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> orderConfirm(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                             @PathVariable("shop_id") Long shopId,
                                             @PathVariable("party_id") Long partyId) {
        managerService.validateShop(principal, shopId);
        shopOrderService.orderConfirm(shopId, partyId);
        sendStatus(partyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{party_id}/reject")
    @Operation(summary = "매니저 : 주문거절", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> orderReject(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                            @PathVariable("shop_id") Long shopId,
                                            @PathVariable("party_id") Long partyId) {
        managerService.validateShop(principal, shopId);
        shopOrderService.orderReject(shopId, partyId);
        sendStatus(partyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{party_id}/made")
    @Operation(summary = "매니저 : 제조완료", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> orderMade(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                          @PathVariable("shop_id") Long shopId,
                                          @PathVariable("party_id") Long partyId) {
        managerService.validateShop(principal, shopId);
        shopOrderService.orderMade(shopId, partyId);
        sendStatus(partyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{party_id}/start-delivery")
    @Operation(summary = "매니저 : 배달시작", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> orderStartDelivery(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                   @PathVariable("shop_id") Long shopId,
                                                   @PathVariable("party_id") Long partyId) {
        managerService.validateShop(principal, shopId);
        shopOrderService.orderStartDelivery(shopId, partyId);
        sendStatus(partyId);
        return ResponseEntity.noContent().build();
    }

    private void sendStatus(Long partyId) {
        PartyStatusInfo partyStatusInfo = partyOrderService.getOrderStatus(partyId);
        PartyStatusResponse partyStatusResponse = partyOrderMapper.toResponse(partyStatusInfo);
        messagingTemplate.convertAndSend(
                "/sub/party/" + partyStatusInfo.accessCode() + "/status",
                partyStatusResponse);
    }

}
