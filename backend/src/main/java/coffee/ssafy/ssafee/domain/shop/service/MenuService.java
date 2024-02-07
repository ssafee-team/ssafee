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

    public List<MenuDetailResponse> getMenusByCategory(Long shopId, Long menuCategoryId) {
        return menuRepository.findAllByShopIdAndMenuCategoryId(shopId, menuCategoryId).stream()
                .map(menuMapper::toDetailDto)
                .toList();
    }

    public Long createMenu(Long shopId, Long menuCategoryId, MenuRequest menuRequest) {
        Menu menu = menuMapper.toEntity(menuRequest);
        menu.setMenuCategory(entityManager.getReference(MenuCategory.class, menuCategoryId));
        menu.setShop(entityManager.getReference(Shop.class, shopId));
        menuRepository.save(menu);
        return menu.getId();
    }

    public void updateMenu(Long shopId, Long menuCategoryId, Long menuId, MenuRequest menuRequest) {
        menuRepository.findByShopIdAndId(shopId, menuId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_MENU))
                .update(menuRequest);
    }

    public void updateMenuImage(Long shopId, Long menuCategoryId, Long menuId, MultipartFile file) {
        menuRepository.findByShopIdAndId(shopId, menuId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_MENU))
                .updateImage(s3Service.putImage(shopId + "/", file));
    }

    public void deleteMenu(Long shopId, Long menuCategoryId, Long menuId) {
        menuRepository.deleteByShopIdAndId(shopId, menuId);
    }

}
