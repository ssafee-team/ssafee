package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopRequestMapper;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopResponseMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopRequestMapper shopRequestMapper;
    private final ShopResponseMapper shopResponseMapper;

    public ShopDetailResponse findShopById(Long id) {
        return shopResponseMapper.toDetailDto(shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP)));
    }

    public List<ShopResponse> findShops() {
        return shopRepository.findAll().stream()
                .map(shopResponseMapper::toDto)
                .toList();
    }

    @Transactional
    public void updateShop(Long id, ShopRequest shopRequest) {
        shopRequestMapper.updateFromDto(shopRequest, shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP)));
    }

}
