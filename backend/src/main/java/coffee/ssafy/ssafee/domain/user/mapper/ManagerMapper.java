package coffee.ssafy.ssafee.domain.user.mapper;

import coffee.ssafy.ssafee.domain.user.dto.response.ManagerResponse;
import coffee.ssafy.ssafee.domain.user.entity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerMapper {

    @Mapping(target = "shopId", source = "shop.id")
    ManagerResponse toDto(Manager manager);

}
