package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.MenuCategoryMapper;
import coffee.ssafy.ssafee.domain.shop.repository.MenuCategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuCategoryService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuCategoryMapper menuCategoryMapper;

    @Transactional(readOnly = true)
    public List<MenuCategoryDetailResponse> findMenuCategories(Long shopId) {
        return menuCategoryRepository.findAllByShopId(shopId).stream()
                .map(menuCategoryMapper::toDetailResponse)
                .toList();
    }

    public Long createMenuCategory(Long shopId, MenuCategoryRequest menuCategoryRequest) {
        MenuCategory menuCategory = MenuCategory.builder()
                .name(menuCategoryRequest.name())
                .shop(entityManager.getReference(Shop.class, shopId))
                .build();
        menuCategoryRepository.save(menuCategory);
        return menuCategory.getId();
    }

    public void updateMenuCategory(Long shopId, Long menuCategoryId, MenuCategoryRequest menuCategoryRequest) {
        menuCategoryRepository.findByShopIdAndId(shopId, menuCategoryId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_MENU_CATEGORY))
                .update(menuCategoryRequest);
    }

    public void deleteMenuCategory(Long shopId, Long menuCategoryId) {
        menuCategoryRepository.deleteByShopIdAndId(shopId, menuCategoryId);
    }

}
