package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.MenuDetailDto;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.ShopMapper;
import coffee.ssafy.ssafee.domain.shop.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final ShopMapper shopMapper;

    public MenuDetailDto findMenuById(Long id) {
        return shopMapper.INSTANCE.toDetailDto(menuRepository.findById(id)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_MENU)));
    }

}
