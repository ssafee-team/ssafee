package coffee.ssafy.ssafee.oauth;

import coffee.ssafy.ssafee.domain.user.entity.User;
import coffee.ssafy.ssafee.domain.user.service.UserService;
import coffee.ssafy.ssafee.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2CustomUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));

        User user = userService.saveUser(email);

        return OAuth2UserImpl.builder()
                .attributes(oAuth2User.getAttributes())
                .authorities(Collections.singletonList(() -> "ROLE_USER"))
                .name(registrationId)
                .id(user.getId())
                .email(email)
                .build();
    }

}
