package coffee.ssafy.ssafee.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "origin")
public record OriginProps(
        String allowed
) {
}
