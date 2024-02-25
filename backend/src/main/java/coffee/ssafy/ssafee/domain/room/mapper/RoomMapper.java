package coffee.ssafy.ssafee.domain.room.mapper;

import coffee.ssafy.ssafee.domain.room.dto.response.RoomDetailResponse;
import coffee.ssafy.ssafee.domain.room.dto.response.RoomResponse;
import coffee.ssafy.ssafee.domain.room.entity.Room;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoomMapper {

    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "userId", source = "user.id")
    RoomResponse toResponse(Room room);

    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "userId", source = "user.id")
    RoomDetailResponse toDetailResponse(Room room);

}
