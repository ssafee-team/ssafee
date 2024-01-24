package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.response.*;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopResponse toDto(Shop shop);

    MenuResponse toDto(Menu menu);

    ShopDetailResponse toDetailDto(Shop shop);

    MenuDetailResponse toDetailDto(Menu menu);

    OptionCategoryDetailResponse toDetailDto(OptionCategory optionCategory);

    Shop toEntity(ShopResponse shopResponse);

}
