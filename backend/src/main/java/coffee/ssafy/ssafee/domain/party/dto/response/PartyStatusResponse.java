package coffee.ssafy.ssafee.domain.party.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_DATETIME_EXAMPLE;
import static coffee.ssafy.ssafee.common.DateUtils.LOCAL_DATETIME_PATTERN;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PartyStatusResponse(
        @JsonFormat(pattern = LOCAL_DATETIME_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_DATETIME_EXAMPLE)
        LocalDateTime confirmedTime,
        @JsonFormat(pattern = LOCAL_DATETIME_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_DATETIME_EXAMPLE)
        LocalDateTime rejectedTime,
        @JsonFormat(pattern = LOCAL_DATETIME_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_DATETIME_EXAMPLE)
        LocalDateTime realOrderedTime,
        @JsonFormat(pattern = LOCAL_DATETIME_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_DATETIME_EXAMPLE)
        LocalDateTime madeTime,
        @JsonFormat(pattern = LOCAL_DATETIME_PATTERN)
        @Schema(implementation = String.class, example = LOCAL_DATETIME_EXAMPLE)
        LocalDateTime deliveredTime
) {
}
