package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.common.ResponseMapper;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopResponseMapper extends ResponseMapper<ShopResponse, Shop> {

    ShopDetailResponse toDetailDto(Shop shop);

    OptionCategoryResponse toDetailDto(OptionCategory optionCategory);

}