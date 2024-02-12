package coffee.ssafy.ssafee.domain.chat.mapper;

import coffee.ssafy.ssafee.domain.chat.dto.request.ChatRequest;
import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface ChatMapper {

//    @Mapping(target = "id", ignore = true)
    Chat toEntity(ChatRequest chatRequest);
    ChatResponse toDto(Chat chat);

    default LocalDateTime map(LocalTime localTime) {
        return LocalDateTime.of(LocalDate.now(), localTime);
    }

    default LocalTime map(LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }

}
