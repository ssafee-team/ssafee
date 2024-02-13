package coffee.ssafy.ssafee.domain.chat.controller;

import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.service.ChatService;
import coffee.ssafy.ssafee.domain.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final ChatService chatService;
    private final PartyService partyService;

    @MessageMapping("/party/{access_code}/enter")
    @SendTo("/sub/party/{access_code}/message")
    public ChatResponse enter(@DestinationVariable("access_code") String accessCode) {
        partyService.findPartyId(accessCode);
        return ChatResponse.builder()
                .name("SYSTEM")
                .content("채팅에 접속했습니다.")
                .createdTime(LocalDateTime.now())
                .build();
    }

    @MessageMapping("/party/{access_code}/chat")
    @SendTo("/sub/party/{access_code}/message")
    public ChatResponse chat(@DestinationVariable("access_code") String accessCode, ChatRequest chatRequest) {
        Long partyId = partyService.findPartyId(accessCode);
        return chatService.createChat(partyId, chatRequest);
    }

}
