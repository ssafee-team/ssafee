package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.response.PartyStatusResponse;
import coffee.ssafy.ssafee.domain.party.service.PartyOrderService;
import coffee.ssafy.ssafee.domain.party.service.PartySocketIoService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parties/{access_code}")
@RequiredArgsConstructor
public class PartyOrderController {

    private final PartySocketIoService partySocketIoService;
    private final PartyOrderService partyOrderService;

    @PostMapping("/order")
    @Operation(summary = "총무 : 주문 요청 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> createOrder(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                            @PathVariable("access_code") String accessCode) {

        Long userId = Long.valueOf(principal.id());
        Long partyId = partyOrderService.createOrder(accessCode);
        isCarrier(accessCode);
        partySocketIoService.sendOrderNotification(partyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order")
    @Operation(summary = "총무 : 주문현황조회")
    public ResponseEntity<PartyStatusResponse> getOrders(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(partyOrderService.getOrders(accessCode));
    }

    @PostMapping("/notice")
    @Operation(summary = "총무 : 알림보내기", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> orderDelivered(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                               @PathVariable("access_code") String accessCode) {
        Long userId = Long.valueOf(principal.id());
        partyOrderService.orderDelivered(accessCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/give-me-money")
    @Operation(summary = "총무 : 송금요청 알림보내기")
    public ResponseEntity<Void> giveMeMoney(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                            @PathVariable("access_code") String accessCode) {
        Long userId = Long.valueOf(principal.id());
        partyOrderService.giveMeMoney(accessCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ad")
    @Operation(summary = "총무 : 커피파티 홍보알림 보내기", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> sendAdvertise(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                              @PathVariable("access_code") String accessCode) {
        Long userId = Long.valueOf(principal.id());
        partyOrderService.sendAdvertise(accessCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/is-carrier")
    @Operation(summary = "배달인원선정")
    public ResponseEntity<Void> isCarrier(@PathVariable("access_code") String accessCode) {
        partyOrderService.isCarrier(accessCode);
        return ResponseEntity.noContent().build();
    }

}
