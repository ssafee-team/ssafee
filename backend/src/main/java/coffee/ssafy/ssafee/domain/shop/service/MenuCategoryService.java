package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryName;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.MenuCategoryMapper;
import coffee.ssafy.ssafee.domain.shop.mapper.MenuMapper;
import coffee.ssafy.ssafee.domain.shop.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;



    public List<String> findMenuCategories(Long shopId) {
        return menuCategoryRepository.findAllByShopId(shopId).stream()
                .map(MenuCategory::getName)
                .toList();
        //        List<MenuCategoryName> menuCategoryName = menuCategoryRepository.findAllCategoryByShopId(shopId);
        //        return menuCategoryMapper.toDtoList(menuCategoryName);
    }

}
