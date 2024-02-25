package coffee.ssafy.ssafee.oauth;

import coffee.ssafy.ssafee.domain.user.entity.User;
import coffee.ssafy.ssafee.domain.user.entity.UserAuthority;
import coffee.ssafy.ssafee.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
public class Oauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .authority(UserAuthority.CREATOR)
                        .email(email)
                        .build()));

        return OAuth2UserImpl.builder()
                .attributes(oAuth2User.getAttributes())
                .authorities(List.of(UserAuthority.CREATOR::name))
                .name(registrationId)
                .id(user.getId())
                .email(email)
                .build();
    }

}
