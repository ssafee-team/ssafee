package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.*;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopDto toDto(Shop shop);

    MenuDto toDto(Menu menu);

    ShopDetailDto toDetailDto(Shop shop);

    MenuDetailDto toDetailDto(Menu menu);

    OptionCategoryDetailDto toDetailDto(OptionCategory optionCategory);

    Shop toEntity(ShopDto shopDto);

}
