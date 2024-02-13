package coffee.ssafy.ssafee.domain.chat.mapper;

import coffee.ssafy.ssafee.domain.chat.dto.response.ChatResponse;
import coffee.ssafy.ssafee.domain.chat.entity.Chat;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatResponse toDto(Chat chat);

}
