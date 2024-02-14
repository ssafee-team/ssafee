package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopDetailResponse toDetailDto(Shop shop);

    ShopResponse toDto(Shop shop);

}
