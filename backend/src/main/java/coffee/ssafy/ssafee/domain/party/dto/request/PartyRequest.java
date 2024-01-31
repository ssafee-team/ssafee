package coffee.ssafy.ssafee.domain.party.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_TIME_HH_MM_EXAMPLE;
import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_TIME_HH_MM_PATTERN;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyRequest(
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
