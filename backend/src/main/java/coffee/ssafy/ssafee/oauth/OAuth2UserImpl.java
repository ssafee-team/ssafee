package coffee.ssafy.ssafee.oauth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Builder
public record OAuth2UserImpl(
        @Getter Map<String, Object> attributes,
        @Getter List<GrantedAuthority> authorities,
        @Getter String name,
        Long id,
        String email
) implements OAuth2User {
}
