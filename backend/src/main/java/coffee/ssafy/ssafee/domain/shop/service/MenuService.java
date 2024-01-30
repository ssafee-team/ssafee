package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.mapper.MenuMapper;
import coffee.ssafy.ssafee.domain.shop.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;


    public List<Menu> getMenusByCategory(Long shopId, Long mcId) {
        return menuList
}
