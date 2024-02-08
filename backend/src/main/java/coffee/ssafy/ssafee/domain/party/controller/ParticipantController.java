package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.request.ParticipantUpdateRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.party.service.ParticipantService;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parties/{access_code}/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final PartyService partyService;
    private final ParticipantService participantService;

    @GetMapping
    @Operation(summary = "참가자 목록")
    public ResponseEntity<List<ParticipantResponse>> getParticipants(@PathVariable("access_code") String accessCode) {
        Long partyId = partyService.findPartyId(accessCode);
        return ResponseEntity.ok().body(participantService.findParticipants(partyId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "참가자 정보 수정")
    public ResponseEntity<Void> updateParticipant(@PathVariable("access_code") String accessCode,
                                                  @PathVariable("id") Long participantId,
                                                  @RequestBody ParticipantUpdateRequest participantUpdateRequest) {
        Long partyId = partyService.findPartyId(accessCode);
        participantService.updateParticipant(partyId, participantId, participantUpdateRequest);
        return ResponseEntity.noContent().build();
    }

}
