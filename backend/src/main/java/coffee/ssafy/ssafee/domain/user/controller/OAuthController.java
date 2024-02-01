package coffee.ssafy.ssafee.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class OAuthController {

    @GetMapping("/google")
    public ResponseEntity<Void> redirectOAuth() {
//        String redirectUrl = "https://accounts.google.com/o/oauth2/v2/auth"
//                + "?client_id={}"
//                + "&redirect_uri=http://ssaffy.coffee/api/v1/oauth/google/callback"
//                + "&response_type=code"
//                + "&scope=openid%20profile%20email";
//        URI location = URI.create("/api/v1/parties/" + accessCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/google/callback")
    public ResponseEntity<Void> callbackOAuth() {
//        URI location = URI.create("/api/v1/parties/" + accessCode);
//        return ResponseEntity.created(location).build();
        return ResponseEntity.ok().build();
    }

}
