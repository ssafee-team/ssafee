package coffee.ssafy.ssafee.oauth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class OAuth2CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver;

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = oAuth2AuthorizationRequestResolver.resolve(request);
        return processAdditionalParameters(request, authorizationRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest = oAuth2AuthorizationRequestResolver.resolve(request, clientRegistrationId);
        return processAdditionalParameters(request, authorizationRequest);
    }

    private OAuth2AuthorizationRequest processAdditionalParameters(HttpServletRequest request, OAuth2AuthorizationRequest authorizationRequest) {
        if (authorizationRequest == null) {
            return null;
        }
        String redirectUri = request.getParameter("redirect_uri");
        if (StringUtils.hasText(redirectUri)) {
            return OAuth2AuthorizationRequest.from(authorizationRequest)
                    .redirectUri(redirectUri)
                    .build();
        }
        return authorizationRequest;
    }
}
