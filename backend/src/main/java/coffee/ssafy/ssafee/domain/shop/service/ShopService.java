package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.ShopDetailDto;
import coffee.ssafy.ssafee.domain.shop.dto.ShopDto;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopDetailDto findShopById(Long id) {
        return shopMapper.INSTANCE.toDetailDto(shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP)));
    }

    public List<ShopDto> findAllShop() {
        return shopRepository.findAll().stream()
                .map(shopMapper.INSTANCE::toDto)
                .toList();
    }

}
