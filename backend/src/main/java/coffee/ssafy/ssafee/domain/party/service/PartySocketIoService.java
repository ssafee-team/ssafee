package coffee.ssafy.ssafee.domain.party.service;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartySocketIoService {

    private final SocketIOServer server;

    // 주문 알림 전송
    public void sendOrderNotification(Long partyId) {
        server.getBroadcastOperations().sendEvent("주문 요청이 도착했습니다", partyId);
    }

}
