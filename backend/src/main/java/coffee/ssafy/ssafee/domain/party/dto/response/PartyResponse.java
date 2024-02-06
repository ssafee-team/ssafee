package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static coffee.ssafy.ssafee.common.DateUtils.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyResponse(
        Long id,
        String name,
        Integer generation,
        Integer classroom,
        @JsonFormat(pattern = LOCAL_TIME_HH_MM_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_TIME_HH_MM_EXAMPLE)
        LocalTime lastOrderTime,
        @JsonFormat(pattern = LOCAL_DATETIME_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_DATETIME_EXAMPLE)
        LocalDateTime createdTime,
        Long shopId,
        OrderResponse orderResponse
) {
}
