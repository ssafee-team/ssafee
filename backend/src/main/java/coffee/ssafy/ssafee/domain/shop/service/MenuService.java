package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuDetailResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.MenuMapper;
import coffee.ssafy.ssafee.domain.shop.repository.MenuRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final S3Service s3Service;

    public Long create(Long shopId, Long menuCategoryId, MenuRequest menuRequest) {
        Menu menu = Menu.builder()
                .name(menuRequest.name())
                .description(menuRequest.description())
                .price(menuRequest.price())
                .soldOut(menuRequest.soldOut())
                .menuCategory(entityManager.getReference(MenuCategory.class, menuCategoryId))
                .shop(entityManager.getReference(Shop.class, shopId))
                .build();
        menuRepository.save(menu);
        return menu.getId();
    }

    @Transactional(readOnly = true)
    public List<MenuDetailResponse> findAll(Long shopId, Long menuCategoryId) {
        return menuRepository.findAllByShopIdAndMenuCategoryId(shopId, menuCategoryId).stream()
                .map(menuMapper::toDetailResponse)
                .toList();
    }

    public void update(Long shopId, Long menuCategoryId, Long menuId, MenuRequest menuRequest) {
        menuRepository.findByShopIdAndId(shopId, menuId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_MENU))
                .update(menuRequest);
    }

    public void updateImage(Long shopId, Long menuCategoryId, Long menuId, MultipartFile file) {
        String prefix = String.format("shop/%d/menu/%d/", shopId, menuId);
        Menu menu = menuRepository.findByShopIdAndId(shopId, menuId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_MENU));
        String imageUrl = menu.getImageUrl();
        menu.updateImageUrl(s3Service.putImage(prefix, file));
        if (imageUrl != null) {
            s3Service.deleteImage(imageUrl);
        }
    }

    public void delete(Long shopId, Long menuCategoryId, Long menuId) {
        menuRepository.deleteByShopIdAndId(shopId, menuId);
    }

}
