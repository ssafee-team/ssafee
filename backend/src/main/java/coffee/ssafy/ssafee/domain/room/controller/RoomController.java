package coffee.ssafy.ssafee.domain.room.controller;

import coffee.ssafy.ssafee.domain.order.service.RoomOrderService;
import coffee.ssafy.ssafee.domain.room.dto.request.RoomRequest;
import coffee.ssafy.ssafee.domain.room.dto.response.RoomDetailResponse;
import coffee.ssafy.ssafee.domain.room.dto.response.RoomResponse;
import coffee.ssafy.ssafee.domain.room.service.RoomService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomOrderService roomOrderService;

    @PostMapping
    @Operation(summary = "방 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> create(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @RequestBody RoomRequest roomRequest) {
        Long userId = principal.id();
        String id = roomService.create(userId, roomRequest);
        URI location = URI.create("/v1/rooms/" + id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "방 목록 조회 (오늘)")
    public ResponseEntity<List<RoomResponse>> readAll(@RequestParam(value = "start_date", required = false) LocalDate startDate,
                                                      @RequestParam(value = "end_date", required = false) LocalDate endDate) {
        return ResponseEntity.ok().body(roomService.findAll(startDate, endDate));
    }

    @GetMapping("/{id}")
    @Operation(summary = "방 조회")
    public ResponseEntity<RoomDetailResponse> read(@PathVariable String id) {
        return ResponseEntity.ok().body(roomService.find(id));
    }

}
