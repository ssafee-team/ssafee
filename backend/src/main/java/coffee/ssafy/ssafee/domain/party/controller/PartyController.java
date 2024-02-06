package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.request.PartyRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import coffee.ssafy.ssafee.domain.party.service.PartySocketIoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;
    private final PartySocketIoService partySocketIoService;

    @PostMapping
//    @Operation(summary = "파티 생성", security = @SecurityRequirement(name = "access-token")) // TODO: 인증 비활성화
//    public ResponseEntity<Void> createParty(@AuthenticationPrincipal JwtPrincipalInfo principal, // TODO: 인증 비활성화
//        Long userId = Long.valueOf(principal.id()); // TODO: 인증 비활성화
    @Operation(summary = "파티 생성")
    public ResponseEntity<Void> createParty(@RequestBody PartyRequest partyRequest) {
        Long userId = 1L;
        String accessCode = partyService.createParty(userId, partyRequest);
        URI location = URI.create("/api/v1/parties/" + accessCode);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "파티 목록 조회 (오늘)")
    public ResponseEntity<List<PartyResponse>> getParties(@RequestParam(value = "start_date", required = false) LocalDate startDate,
                                                          @RequestParam(value = "end_date", required = false) LocalDate endDate) {
        return ResponseEntity.ok().body(partyService.findParties(startDate, endDate));
    }

    @GetMapping("/{access_code}")
    @Operation(summary = "파티 조회")
    public ResponseEntity<PartyDetailResponse> getParty(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(partyService.findPartyByAccessCode(accessCode));
    }

}
