package coffee.ssafy.ssafee.domain.party.mapper;

import coffee.ssafy.ssafee.domain.party.dto.request.PartyRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyDetailResponse;
import coffee.ssafy.ssafee.domain.party.dto.response.PartyResponse;
import coffee.ssafy.ssafee.domain.party.entity.OrderMenu;
import coffee.ssafy.ssafee.domain.party.entity.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface PartyMapper {

    @Mapping(target = "shopId", source = "shop.id")
    PartyResponse toDto(Party party);

    OrderMenuResponse toDto(OrderMenu orderMenu);

    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "userId", source = "user.id")
    PartyDetailResponse toDetailDto(Party party);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "accessCode", ignore = true)
    @Mapping(target = "shop", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "orderMenus", ignore = true)
    Party toEntity(PartyRequest partyRequest);

    default LocalDateTime map(LocalTime localTime) {
        return LocalDateTime.of(LocalDate.now(), localTime);
    }

    default LocalTime map(LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }

}
