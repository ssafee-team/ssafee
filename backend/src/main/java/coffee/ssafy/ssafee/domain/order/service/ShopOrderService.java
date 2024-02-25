package coffee.ssafy.ssafee.domain.order.service;

import coffee.ssafy.ssafee.domain.order.dto.response.OrderDetailResponse;
import coffee.ssafy.ssafee.domain.order.entity.Order;
import coffee.ssafy.ssafee.domain.order.mapper.OrderMapper;
import coffee.ssafy.ssafee.domain.order.repository.OrderRepository;
import coffee.ssafy.ssafee.domain.room.exception.RoomErrorCode;
import coffee.ssafy.ssafee.domain.room.exception.RoomException;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopOrderService {

    private final ShopRepository shopRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderDetailResponse> findAll(Long shopId) {
        return orderRepository.findAllByShopId(shopId).stream()
                .map(orderMapper::toDetailResponse)
                .toList();
    }

    public String confirm(Long shopId, Long orderId) {
        // 1. 유효한 파티인가?
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));
        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: Rejected field == null?
        // 3. confirmed_time 을 현재 시간으로 업데이트
        order.updateConfirm();
        return order.getRoom().getId();
    }

    public String reject(Long shopId, Long orderId) {
        // 1. 유효한 파티인가?
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));

        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: confirmed field == null?
        // 3. rejected_time 을 현재 시간으로 업데이트
        order.updateReject();
        return order.getRoom().getId();
    }

    public String make(Long shopId, Long orderId) {
        // 1. 유효한 파티인가?
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));

        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: confirmed field == null?
        // 3. made_time 을 현재 시간으로 업데이트
        order.updateMake();
        return order.getRoom().getId();
    }

    public String deliver(Long shopId, Long orderId) {
        // 1. 유효한 파티인가?
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));

        // 2. 유효한 shop_id 인가?
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
        // TODO: made field == null?
        return order.getRoom().getId();
    }

}
