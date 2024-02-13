package coffee.ssafy.ssafee.domain.party.service;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartySocketIOService {

    private final SocketIOServer server;

    // 주문요청알림 전송
    public void sendOrderNotification(Long partyId) {
        server.getBroadcastOperations().sendEvent("orderRequest", partyId);
    }

    // 배달완료알림 전송 + webHookUrl
    public void sendCompleteDeliveryNotification(Long partyId) {
        server.getBroadcastOperations().sendEvent("completeDelivery", partyId);
    }

}
