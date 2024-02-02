package coffee.ssafy.ssafee.oauth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class OAuth2UserImpl implements OAuth2User {

    private final Map<String, Object> attributes;
    private final List<GrantedAuthority> authorities;
    private final String name;
    private final Long id;
    private final String email;

}
