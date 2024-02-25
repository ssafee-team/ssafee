package coffee.ssafy.ssafee.domain.room.controller;

import coffee.ssafy.ssafee.domain.room.service.CreatorService;
import coffee.ssafy.ssafee.domain.room.service.RoomService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rooms/{roomId}/creator")
@RequiredArgsConstructor
public class CreatorController {

    private final RoomService roomService;
    private final CreatorService creatorService;

    @PostMapping("/advertise")
    @Operation(summary = "총무 : 커피파티 홍보 알림", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> advertise(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                          @PathVariable String roomId) {
        roomService.validatePrincipal(roomId, principal.id());
        creatorService.advertise(roomId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notice-carriers")
    @Operation(summary = "총무 : 배달부 선정 알림", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> noticeCarriers(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                               @PathVariable String roomId) {
        roomService.validatePrincipal(roomId, principal.id());
        creatorService.noticeCarriers(roomId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notice-delivered")
    @Operation(summary = "총무 : 배달 도착 알림", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> noticeDelivered(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                @PathVariable String roomId) {
        roomService.validatePrincipal(roomId, principal.id());
        creatorService.noticeDelivered(roomId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/give-me-money")
    @Operation(summary = "총무 : 송금요청 알림", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> giveMeMoney(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                            @PathVariable String roomId) {
        roomService.validatePrincipal(roomId, principal.id());
        creatorService.giveMeMoney(roomId);
        return ResponseEntity.ok().build();
    }

}
