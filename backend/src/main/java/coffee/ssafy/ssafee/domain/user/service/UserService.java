package coffee.ssafy.ssafee.domain.user.service;

import coffee.ssafy.ssafee.domain.user.entity.User;
import coffee.ssafy.ssafee.domain.user.repository.UserRepository;
import coffee.ssafy.ssafee.oauth.OAuth2UserImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public final String ROLE_USER = "ROLE_USER";
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));

        log.info("email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .build()));

        return OAuth2UserImpl.builder()
                .attributes(oAuth2User.getAttributes())
                .authorities(List.of(() -> ROLE_USER))
                .name(registrationId)
                .id(user.getId())
                .email(email)
                .build();
    }

}
