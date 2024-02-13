package coffee.ssafy.ssafee.domain.shop.service;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopOrderSocketIOService {

    private final SocketIOServer socketIOServer;

    // 주문수락알림 전송
    public void sendConfirmNotification(Long partyId) {
        socketIOServer.getBroadcastOperations().sendEvent("confirmed", partyId);
    }

    // 주문거절알림 전송
    public void sendRejectNotification(Long partyId) {
        socketIOServer.getBroadcastOperations().sendEvent("rejected", partyId);
    }

    // 제조완료알림 전송
    public void sendMadeNotification(Long partyId) {
        socketIOServer.getBroadcastOperations().sendEvent("made", partyId);
    }

    // 배달시작알림 전송
    public void sendStartDeliveryNotification(Long partyId) {
        socketIOServer.getBroadcastOperations().sendEvent("startDelivery", partyId);
    }

}
