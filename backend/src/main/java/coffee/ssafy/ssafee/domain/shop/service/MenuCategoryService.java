package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
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

    public List<MenuCategoryResponse> findMenuCategories(Long shopId) {
        return menuCategoryRepository.findAllByShopId(shopId).stream()
                .map(menuCategoryMapper::toDto)
                .toList();
    }

    public Long createMenuCategory(Long shopId, MenuCategoryRequest menuCategoryRequest) {
        MenuCategory menuCategory = menuCategoryMapper.toEntity(menuCategoryRequest);
        menuCategory.setShop(entityManager.getReference(Shop.class, shopId));
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
