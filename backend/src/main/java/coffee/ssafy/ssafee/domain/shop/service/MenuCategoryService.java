package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
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
        return menuCategoryMapper.toDtoList(menuCategoryRepository.findAllByShopId(shopId));
    }

    public Long createMenuCategories(Long shopId, MenuCategoryRequest menuCategoryRequest) {
        MenuCategory menuCategory = menuCategoryMapper.toEntity(menuCategoryRequest);
        menuCategory.setShop(entityManager.getReference(Shop.class, shopId));
        menuCategoryRepository.save(menuCategory);
        return menuCategory.getId();
    }

    public void updateMenuCategory(Long menuCategoryId, MenuCategoryRequest menuCategoryRequest) {
        menuCategoryRepository.findById(menuCategoryId).ifPresent(menuCategory -> {
            menuCategoryMapper.updateMenuCategory(menuCategory, menuCategoryRequest);
            menuCategoryRepository.save(menuCategory);
        });
    }

    public void deleteMenuCategory(Long menuCategoryId) {
        menuCategoryRepository.deleteById(menuCategoryId);
    }

}
