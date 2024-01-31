package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.mapper.MenuMapper;
import coffee.ssafy.ssafee.domain.shop.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;


    public List<MenuResponse> getMenusByCategory(Long shopId, Long menuCategoryId) {
        List<Menu> menus = menuRepository.findByShopIdAndMenuCategoryId(shopId, menuCategoryId);
        return menus.stream()
                .map(menuMapper::toDto).
                collect(Collectors.toList());

    }
}
