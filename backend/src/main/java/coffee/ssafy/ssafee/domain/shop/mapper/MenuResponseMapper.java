package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.common.ResponseMapper;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuResponseMapper extends ResponseMapper<MenuResponse, Menu> {
}
