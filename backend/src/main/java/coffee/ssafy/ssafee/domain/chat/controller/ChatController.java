package coffee.ssafy.ssafee.domain.chat.controller;

import coffee.ssafy.ssafee.domain.chat.dto.SocketVO;
import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import coffee.ssafy.ssafee.domain.chat.service.ChatService;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.array.ArrayToStringFunction;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/parties/{access_code}/chats")
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class ChatController {

    private final ChatService chatService;

//    @PostMapping
//    @Operation(summary = "채팅 생성")
//    public ResponseEntity<Void> createChat(@PathVariable("access_code") String accessCode,
//                                           @RequestBody ChatRequest chatRequest) {
//        System.out.println(accessCode);
////        chatService.createChat(accessCode, chatRequest);
//        return ResponseEntity.noContent().build();
//    }


//    @GetMapping
//    @Operation(summary = "채팅 기록 조회")
//    public ResponseEntity<List<ChatResponse>> getChats(@PathVariable("access_code") String accessCode) {
//        return ResponseEntity.ok().body(chatService.getChats(accessCode));
//    }
//
//    @MessageMapping("/receive")
//    @SendTo("/send")
//    public void chatHandler(String accessCode, ChatRequest chatRequest) {
//        chatService.createChat(accessCode, chatRequest);
//    }

//    @MessageMapping("/receive")
//    @SendTo("/send")
//    public Chat SocketHandler(Chat chat) {
//        String name = chat.getUserName();
//        String content = chat.getContent();
//        String contentTime = chat.getContentTime();
//
//        Chat result = new Chat(name, content, contentTime);
//
//        System.out.println(result.getUserName() + ", " + result.getContent() + ", " + result.getContentTime());
//        return result;
//    }
//    @MessageMapping("/receive")
    @MessageMapping("/receive/{accessCode}")
    @SendTo("/send/{accessCode}")
    public ChatRequest SocketHandler(@DestinationVariable String accessCode, ChatRequest chatRequest) {
        String name = chatRequest.userName();
        String content = chatRequest.content();
        String contentTime = chatRequest.contentTime();

        ChatRequest result = new ChatRequest(name, content, contentTime);

        chatService.saveChat(accessCode, chatRequest);

        System.out.println(result.userName() + ", " + result.content() + ", " + result.contentTime());
        return result;
    }

}
