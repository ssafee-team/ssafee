package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.common.RequestMapper;
import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopRequestMapper extends RequestMapper<ShopRequest, Shop> {
}
