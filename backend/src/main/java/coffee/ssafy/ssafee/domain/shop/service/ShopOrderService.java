package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.ShopOrderResponse;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import coffee.ssafy.ssafee.domain.party.mapper.OrderMenuMapper;
import coffee.ssafy.ssafee.domain.party.repository.PartyRepository;
import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopMapper;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopOrderMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopOrderService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final PartyRepository partyRepository;
    private final OrderMenuMapper orderMenuMapper;
    private final ShopOrderMapper shopOrderMapper;

    public List<List<OrderMenuResponse>> findShopOrders(Long shopId) {
        // shopId로 파티 목록을 끌어온다
        // 파티 안에 들은 List<OrderMenu>를 가져온다.
        // 클라이언트한테 뿌린다.
        return partyRepository.findAllByShopId(shopId).stream()
                .filter(p -> p.getOrder() != null)
                .map(shopOrderMapper::toDto)
//                .map(Party::getOrderMenus)
//                .map(omss -> omss.stream()
//                        .map(orderMenuMapper::toDto)
//                        .toList())
                .toList();
    }

}
