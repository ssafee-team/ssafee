package coffee.ssafy.ssafee.domain.chat.controller;

import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.service.ChatService;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.List;


@RestController
@RequestMapping("/api/v1/parties/{access_code}/chats")
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class ChatController {

    private final ChatService chatService;
    private final PartyService partyService;

    @GetMapping
    @Operation(summary = "채팅 기록 조회")
    public ResponseEntity<List<ChatResponse>> getChats(@PathVariable("access_code") String accessCode) {
        Long partyId = partyService.findPartyId(accessCode);
        return ResponseEntity.ok().body(chatService.findChats(partyId));
    }

    @MessageMapping("/receive/{accessCode}")
    @SendTo("/send/{accessCode}")
    public ChatRequest SocketHandler(@DestinationVariable String accessCode, ChatRequest chatRequest) {
        Long partyId = partyService.findPartyId(accessCode);
        chatService.saveChat(partyId, chatRequest);
        return chatRequest;
    }

}
