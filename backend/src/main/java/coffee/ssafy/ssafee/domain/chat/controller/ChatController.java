package coffee.ssafy.ssafee.domain.chat.controller;

import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.service.ChatService;
import coffee.ssafy.ssafee.domain.room.service.RoomService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/rooms/{roomId}/chats")
@RequiredArgsConstructor
public class ChatController {

    private final RoomService roomService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    @Operation(summary = "채팅 기록 조회")
    public ResponseEntity<Void> create(@PathVariable String roomId,
                                       @RequestBody ChatRequest chatRequest) {
        var chatResponse = chatService.create(roomId, chatRequest);
        messagingTemplate.convertAndSend("/sub/rooms/" + roomId + "/chats/create", chatResponse);
        var location = URI.create("/v1/rooms/" + chatResponse.id());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "채팅 기록 조회")
    public ResponseEntity<List<ChatResponse>> readAll(@PathVariable String roomId) {
        return ResponseEntity.ok().body(chatService.findAll(roomId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "채팅 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> readAll(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                        @PathVariable String roomId,
                                        @PathVariable Long id) {
        roomService.validatePrincipal(roomId, principal.id());
        chatService.delete(roomId, id);
        messagingTemplate.convertAndSend("/sub/rooms/" + roomId + "/chats/delete", id);
        return ResponseEntity.noContent().build();
    }

}
