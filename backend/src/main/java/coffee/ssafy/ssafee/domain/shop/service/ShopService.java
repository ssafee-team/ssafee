package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopMapper;
import coffee.ssafy.ssafee.domain.shop.repository.ShopRepository;
import coffee.ssafy.ssafee.domain.user.exception.UserErrorCode;
import coffee.ssafy.ssafee.domain.user.exception.UserException;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
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

    @Transactional(readOnly = true)
    public ShopDetailResponse find(Long id) {
        return shopRepository.findById(id)
                .map(shopMapper::toDetailResponse)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));
    }

    @Transactional(readOnly = true)
    public List<ShopDetailResponse> findAll() {
        return shopRepository.findAll().stream()
                .map(shopMapper::toDetailResponse)
                .toList();
    }

    public void update(Long id, ShopRequest shopRequest) {
        shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP))
                .update(shopRequest);
    }

    public void updateImage(Long id, MultipartFile file) {
        String prefix = String.format("shop/%d/", id);
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_SHOP));

        String image = shop.getImageUrl();
        shop.updateImageUrl(s3Service.putImage(prefix, file));
        if (image != null) {
            s3Service.deleteImage(image);
        }
    }

    @Transactional(readOnly = true)
    public void validatePrincipal(JwtPrincipalInfo principal, Long shopId) {
        if (!principal.shopId().equals(shopId)) {
            throw new UserException(UserErrorCode.UNAUTHORIZED_SHOP);
        }
    }

}
