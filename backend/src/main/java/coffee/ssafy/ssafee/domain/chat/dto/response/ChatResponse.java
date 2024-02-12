package coffee.ssafy.ssafee.domain.chat.dto.response;

import coffee.ssafy.ssafee.domain.party.entity.Party;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_DATETIME_EXAMPLE;
import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_DATETIME_PATTERN;

//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatResponse(
        Long id,

        @JsonFormat(pattern = LOCAL_DATETIME_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_DATETIME_EXAMPLE)
        LocalDateTime created_time,

        Party party,

        String userName,
        String content,

        String content_time

) {
}
