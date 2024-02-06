package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailForManagerResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.PartyInfoForManagerResponse;
import coffee.ssafy.ssafee.domain.shop.service.ShopOrderService;
import coffee.ssafy.ssafee.domain.shop.service.ShopOrderSocketIoService;
import coffee.ssafy.ssafee.domain.user.service.ManagerService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/orders")
@RequiredArgsConstructor
public class ShopOrderController {

    private final ShopOrderSocketIoService shopOrderSocketIoService;
    private final ShopOrderService shopOrderService;
    private final ManagerService managerService;

    // 사장이 내 가게의 과거 주문내역을 다 뽑는거.
    @GetMapping("/")
    @Operation(summary = "매니저: 주문기록조회")
    public ResponseEntity<List<PartyInfoForManagerResponse>> getShopHistory(
            @PathVariable("shop_id") Long shopId) {
        return ResponseEntity.ok().body(shopOrderService.getShopHistories(shopId));
    }

    @GetMapping("/{party_id}/confirm")
    @Operation(summary = "매니저 : 주문기록상세조회", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<List<PartyDetailForManagerResponse>> getShopHistoryDetail(
            @AuthenticationPrincipal JwtPrincipalInfo principal,
            @PathVariable("shop_id") Long shopId,
            @PathVariable("party_id") Long partyId) {
        managerService.validateShop(principal, shopId);
        return ResponseEntity.ok().body(shopOrderService.getShopHistoriesDetail(shopId, partyId));
    }

    @PostMapping("/{party_id}/confirm")
    @Operation(summary = "매니저 : 주문확인", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> orderConfirm(
            @AuthenticationPrincipal JwtPrincipalInfo principal,
            @PathVariable("shop_id") Long shopId,
            @PathVariable("party_id") Long partyId) {
        // 사장님이 "주문수락" 버튼 클릭
        // 파티 confirmTime update
        managerService.validateShop(principal, shopId);
        shopOrderService.orderConfirm(shopId, partyId);
        shopOrderSocketIoService.sendConfirmNotification(partyId);
        return ResponseEntity.noContent().build();
    }

}
