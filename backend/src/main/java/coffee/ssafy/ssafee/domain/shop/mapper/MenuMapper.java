package coffee.ssafy.ssafee.domain.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    // 매퍼클래스에서 MenuMapper를 찾을 수 있도록 하기 위함
    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

}
