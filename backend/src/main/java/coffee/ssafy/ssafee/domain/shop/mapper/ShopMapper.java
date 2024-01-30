package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopDetailResponse toDetailDto(Shop shop);

    OptionCategoryResponse toDetailDto(OptionCategory optionCategory);

    List<ShopResponse> toDtoList(List<Shop> shops);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ShopRequest shopRequest, @MappingTarget Shop shop);

}
