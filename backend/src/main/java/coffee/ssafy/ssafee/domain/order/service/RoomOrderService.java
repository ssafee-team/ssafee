package coffee.ssafy.ssafee.domain.order.service;

import coffee.ssafy.ssafee.domain.order.dto.OrderInfo;
import coffee.ssafy.ssafee.domain.order.dto.response.OrderResponse;
import coffee.ssafy.ssafee.domain.order.entity.Order;
import coffee.ssafy.ssafee.domain.order.exception.OrderErrorCode;
import coffee.ssafy.ssafee.domain.order.exception.OrderException;
import coffee.ssafy.ssafee.domain.order.mapper.OrderMapper;
import coffee.ssafy.ssafee.domain.order.repository.OrderRepository;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceMenu;
import coffee.ssafy.ssafee.domain.room.entity.ChoiceOption;
import coffee.ssafy.ssafee.domain.room.entity.Participant;
import coffee.ssafy.ssafee.domain.room.exception.RoomErrorCode;
import coffee.ssafy.ssafee.domain.room.exception.RoomException;
import coffee.ssafy.ssafee.domain.room.repository.ChoiceMenuRepository;
import coffee.ssafy.ssafee.domain.room.repository.ChoiceOptionRepository;
import coffee.ssafy.ssafee.domain.room.repository.ParticipantRepository;
import coffee.ssafy.ssafee.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomOrderService {

    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final ChoiceOptionRepository choiceOptionRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderInfo create(String roomId) {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ROOM));
        if (orderRepository.existsByRoomId(roomId)) {
            throw new OrderException(OrderErrorCode.ALREADY_ORDERED);
        }

        List<ChoiceMenu> choiceMenu = choiceMenuRepository.findAllByRoomId(roomId);
        List<ChoiceOption> choiceOption = choiceOptionRepository.findAllByRoomId(roomId);
        var totalPrice = choiceMenu.stream().mapToInt(choice -> choice.getMenu().getPrice()).sum()
                         + choiceOption.stream().mapToInt(choice -> choice.getOption().getPrice()).sum();
        if (totalPrice < room.getShop().getMinimumPrice()) {
            throw new OrderException(OrderErrorCode.LESS_THAN_MINIMUM_PRICE);
        }

        Order order = Order.builder()
                .room(room)
                .shop(room.getShop())
                .build();
        orderRepository.save(order);
        return orderMapper.toInfo(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse find(String roomId) {
        return orderRepository.findByRoomId(roomId)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_FOUND_ORDER));
    }

    public void pickCarriers(String roomId) {
        var participants = participantRepository.findAllByRoomId(roomId);
        var carrierCount = (participants.size() + 5) / 6;
        Collections.shuffle(participants);
        participants.subList(0, carrierCount).forEach(Participant::updatePickedCarrier);
    }

}
