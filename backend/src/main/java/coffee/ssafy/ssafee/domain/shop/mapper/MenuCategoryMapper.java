package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryName;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {

    // <반환타입> 함수이름(<입력타입> 입력이름)
    //    response toDto(entity shop);
    //    entity toEntity(request menuCategory);
    //    @Mapping(target = [반환타입], source = [])



    // MenuCategoryName 타입을 String으로 변환하는 매핑 메소드
    default String map(MenuCategoryName value) {
        return value.name(); // record에 대한 직접적인 접근
    }
}
