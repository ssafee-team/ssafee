//package coffee.ssafy.ssafee.domain.chat.controller;
//
//import coffee.ssafy.ssafee.domain.chat.dto.SocketVO;
//import coffee.ssafy.ssafee.domain.chat.entity.Chat;
//import coffee.ssafy.ssafee.domain.party.entity.Party;
//import coffee.ssafy.ssafee.domain.party.service.PartyService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//
//import java.time.LocalDateTime;
//
////@RequestMapping("/api/v1/parties/{access_code}/chats")
//@RestController
//@EnableWebSocketMessageBroker
//@RequiredArgsConstructor
//public class ChatSocketController {
//    PartyService partyService;
//
////    @MessageMapping("/receive")
////    @SendTo("/send")
////    public SocketVO SocketHandler(SocketVO socketVO) {
////        String name = socketVO.getUserName();
////        String content = socketVO.getContent();
////        String contentTime = socketVO.getContentTime();
////
////        SocketVO result = new SocketVO(name, content, contentTime);
////
////        System.out.println("-----" + result);
////        // 반환
////        return result;
////    }
//
//    @MessageMapping("/receive")
//    @SendTo("/send")
//    public SocketVO SocketHandler(SocketVO socketVO) {
//        String name = socketVO.getUserName();
//        String content = socketVO.getContent();
//        String contentTime = socketVO.getContentTime();
//
//        SocketVO result = new SocketVO(name, content, contentTime);
//        // 반환
//        return result;
//    }
//}
