package coffee.ssafy.ssafee.domain.shop.service;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopOrderSocketIoService {

    private final SocketIOServer server;

    // 주문수락알림 전송
    public void sendConfirmNotification(Long partyId) {
        server.getBroadcastOperations().sendEvent("confirmed", partyId);
    }

    // 주문거절알림 전송
    public void sendRejectNotification(Long partyId) {
        server.getBroadcastOperations().sendEvent("rejected", partyId);
    }

    // 제조완료알림 전송
    public void sendMadeNotification(Long partyId) {
        server.getBroadcastOperations().sendEvent("made", partyId);
    }

    // 배달시작알림 전송
    public void sendStartDeliveryNotification(Long partyId) {
        server.getBroadcastOperations().sendEvent("startDelivery", partyId);
    }

}
