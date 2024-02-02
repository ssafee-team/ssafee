package coffee.ssafy.ssafee.domain.user.controller;

import coffee.ssafy.ssafee.oauth.OAuth2CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2CustomUserService oAuth2UserService;

    @GetMapping("/authorization/google")
    public ResponseEntity<Void> redirectOAuth() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/callback/google")
    public ResponseEntity<Void> callbackOAuth() {
        return ResponseEntity.ok().build();
    }

}
