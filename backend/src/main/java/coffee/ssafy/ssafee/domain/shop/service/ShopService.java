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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final S3Service s3Service;

    public ShopDetailResponse findShopById(Long id) {
        return shopRepository.findById(id)
                .map(shopMapper::toDetailDto)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
    }

    public List<ShopResponse> findShops() {
        return shopRepository.findAll().stream()
                .map(shopMapper::toDto)
                .toList();
    }

    public void updateShop(Long id, ShopRequest shopRequest) {
        shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP))
                .update(shopRequest);
    }

    public void updateShopImage(Long id, MultipartFile file) {
        shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP))
                .updateImage(s3Service.putImage(id + "/", file));
    }

}
