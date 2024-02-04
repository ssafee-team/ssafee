package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.request.ParticipantUpdateRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.party.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parties/{access_code}/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping
    @Operation(summary = "참가자 목록")
    public ResponseEntity<List<ParticipantResponse>> getParticipant(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(participantService.findParticipantsByAccessCode(accessCode));
    }

    @PutMapping("/{id}")
    @Operation(summary = "참가자 정보 수정")
    public ResponseEntity<Void> updateParticipant(@PathVariable("access_code") String accessCode,
                                                  @PathVariable("id") Long id,
                                                  @RequestBody ParticipantUpdateRequest participantUpdateRequest) {
        participantService.updateParticipantByAccessCodeAndId(accessCode, id, participantUpdateRequest);
        return ResponseEntity.noContent().build();
    }

}
