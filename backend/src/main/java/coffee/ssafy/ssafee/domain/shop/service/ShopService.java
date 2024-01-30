package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopDetailResponse findShopById(Long id) {
        return shopMapper.toDetailDto(shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP)));
    }

    public List<ShopResponse> findShops() {
        return shopMapper.toDtoList(shopRepository.findAll());
    }

    @Transactional
    public void updateShop(Long id, ShopRequest shopRequest) {
        shopMapper.updateFromDto(shopRequest, shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP)));
    }

}
