package coffee.ssafy.ssafee.domain.chat.controller;

import coffee.ssafy.ssafee.domain.chat.dto.SocketVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@RequestMapping("/api/v1/parties/{access_code}/chats")
@RestController
@EnableWebSocketMessageBroker
public class ChatSocketController {
    @MessageMapping("/receive")

    @SendTo("/send")

    public SocketVO SocketHandler(SocketVO socketVO) {
        String userName = socketVO.getUserName();
        String content = socketVO.getContent();
        String contentTime = socketVO.getContentTime();

        SocketVO result = new SocketVO(userName, content, contentTime);
        // 반환
        return result;
    }
}
