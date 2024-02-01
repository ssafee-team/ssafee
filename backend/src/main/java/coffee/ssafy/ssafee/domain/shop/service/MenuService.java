package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.mapper.MenuMapper;
import coffee.ssafy.ssafee.domain.shop.repository.MenuRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final EntityManager entityManager;
    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;


    public List<MenuResponse> getMenusByCategory(Long shopId, Long menuCategoryId) {
        List<Menu> menus = menuRepository.findByShopIdAndMenuCategoryId(shopId, menuCategoryId);
        return menus.stream()
                .map(menuMapper::toDto).
                collect(Collectors.toList());

    }

    public Long createMenu(Long shopId, Long menuCategoryId, MenuRequest menuRequest) {
        Menu menu = menuMapper.toEntity(menuRequest);
        menu.setMenuCategory(entityManager.getReference(MenuCategory.class, menuCategoryId));
        menu.setShop(entityManager.getReference(Shop.class, shopId));
        menuRepository.save(menu);
        return menu.getId();
    }

    @Transactional
    public void updateMenu(Long menuId, MenuRequest menuRequest) {
        menuRepository.findById(menuId).ifPresent(menu -> {
            menuMapper.updateMenu(menu, menuRequest);
            menuRepository.save(menu);
        });
    }

}
