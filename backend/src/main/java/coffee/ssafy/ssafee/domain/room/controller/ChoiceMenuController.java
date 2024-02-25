package coffee.ssafy.ssafee.domain.room.controller;

import coffee.ssafy.ssafee.domain.chat.dto.ChatInfo;
import coffee.ssafy.ssafee.domain.chat.service.ChatService;
import coffee.ssafy.ssafee.domain.room.dto.request.ChoiceMenuCreateRequest;
import coffee.ssafy.ssafee.domain.room.dto.response.ChoiceMenuResponse;
import coffee.ssafy.ssafee.domain.room.service.ChoiceMenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/rooms/{roomId}/choice-menus")
@RequiredArgsConstructor
public class ChoiceMenuController {

    private final ChoiceMenuService choiceMenuService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    @Operation(summary = "주문 메뉴 생성")
    public ResponseEntity<Void> create(@PathVariable String roomId,
                                       @RequestBody ChoiceMenuCreateRequest choiceMenuCreateRequest) {
        var choiceMenuResponse = choiceMenuService.create(roomId, choiceMenuCreateRequest);
        messagingTemplate.convertAndSend("/sub/rooms/" + roomId + "/choice-menus/create", choiceMenuResponse);
        var chatResponse = chatService.create(roomId, ChatInfo.builder()
                .name("SYSTEM")
                .content("<" + choiceMenuResponse.participantName() + ">님의 <" + choiceMenuResponse.menu().name() + "> 주문 생성")
                .build());
        messagingTemplate.convertAndSend("/sub/rooms/" + roomId + "/chats/create", chatResponse);
        var location = URI.create("/v1/rooms/" + roomId + "/choice-menus/" + choiceMenuResponse.id());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "주문 메뉴 목록")
    public ResponseEntity<List<ChoiceMenuResponse>> readAll(@PathVariable String roomId) {
        return ResponseEntity.ok().body(choiceMenuService.findAll(roomId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "주문 메뉴 조회")
    public ResponseEntity<ChoiceMenuResponse> read(@PathVariable String roomId,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok().body(choiceMenuService.find(roomId, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "주문 메뉴 삭제")
    public ResponseEntity<Void> delete(@PathVariable String roomId,
                                       @PathVariable Long id) {
        var choiceMenuResponse = choiceMenuService.delete(roomId, id);
        messagingTemplate.convertAndSend("/sub/rooms/" + roomId + "/choice-menus/delete", id);
        var chatResponse = chatService.create(roomId, ChatInfo.builder()
                .name("SYSTEM")
                .content("<" + choiceMenuResponse.participantName() + ">님의 <" + choiceMenuResponse.menu().name() + "> 주문 삭제")
                .build());
        messagingTemplate.convertAndSend("/sub/rooms/" + roomId + "/chats/create", chatResponse);
        return ResponseEntity.noContent().build();
    }

}
