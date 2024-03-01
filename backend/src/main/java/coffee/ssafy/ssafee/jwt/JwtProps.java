package coffee.ssafy.ssafee.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "jwt")
public record JwtProps(
        String accessSecretKeyBase64,
        @DurationUnit(ChronoUnit.SECONDS) Duration accessExpirationSeconds,
        String refreshSecretKeyBase64,
        @DurationUnit(ChronoUnit.SECONDS) Duration refreshExpirationSeconds
) {
}
