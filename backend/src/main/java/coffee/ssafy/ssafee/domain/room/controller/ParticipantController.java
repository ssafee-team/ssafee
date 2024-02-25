package coffee.ssafy.ssafee.domain.room.controller;

import coffee.ssafy.ssafee.domain.room.dto.request.ParticipantUpdateRequest;
import coffee.ssafy.ssafee.domain.room.dto.response.ParticipantResponse;
import coffee.ssafy.ssafee.domain.room.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rooms/{roomId}/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping
    @Operation(summary = "참가자 목록")
    public ResponseEntity<List<ParticipantResponse>> readAll(@PathVariable String roomId) {
        return ResponseEntity.ok().body(participantService.findAll(roomId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "참가자 정보 수정")
    public ResponseEntity<Void> update(@PathVariable String roomId,
                                       @PathVariable Long id,
                                       @RequestBody ParticipantUpdateRequest participantUpdateRequest) {
        participantService.update(roomId, id, participantUpdateRequest);
        return ResponseEntity.noContent().build();
    }

}
