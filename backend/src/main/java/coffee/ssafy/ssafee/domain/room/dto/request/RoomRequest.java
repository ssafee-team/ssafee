package coffee.ssafy.ssafee.domain.room.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_TIME_HH_MM_EXAMPLE;
import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_TIME_HH_MM_PATTERN;

public record RoomRequest(
        String name,
        Integer generation,
        Integer classroom,
        @JsonFormat(pattern = LOCAL_TIME_HH_MM_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_TIME_HH_MM_EXAMPLE)
        LocalTime lastOrderTime,
        Long shopId,
        CreatorRequest creator
) {
}
