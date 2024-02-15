package coffee.ssafy.ssafee.oauth;

import coffee.ssafy.ssafee.jwt.JwtTokenProvider;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2UserImpl oAuth2User = (OAuth2UserImpl) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.issueUserAccessToken(JwtPrincipalInfo.builder()
                .id(String.valueOf(oAuth2User.id()))
                .info(oAuth2User.email())
                .role(oAuth2User.authorities().get(0).getAuthority())
                .build());

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        clearAuthenticationAttributes(request, response);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
    }

}
