package coffee.ssafy.ssafee.jwt;

import coffee.ssafy.ssafee.jwt.dto.JwtTokenInfo;
import coffee.ssafy.ssafee.jwt.exception.JwtTokenErrorCode;
import coffee.ssafy.ssafee.jwt.exception.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String CLAIMS_ID = "id";
    private static final String CLAIMS_ROLE = "role";
    private final JwtProps jwtProps;

    public String issueAccessToken(JwtTokenInfo jwtTokenInfo) {
        Claims claims = Jwts.claims()
                .add(CLAIMS_ID, jwtTokenInfo.id())
                .add(CLAIMS_ROLE, jwtTokenInfo.role())
                .build();
        return issueToken(claims, jwtProps.getAccessExpiration(), jwtProps.getAccessSecretKey());
    }

    public String issueRefreshToken() {
        return issueToken(null, jwtProps.getRefreshExpiration(), jwtProps.getRefreshSecretKey());
    }

    public JwtTokenInfo parseAccessToken(String accessToken) {
        Claims claims = parseToken(accessToken, jwtProps.getAccessSecretKey());
        return JwtTokenInfo.builder()
                .id(claims.get(CLAIMS_ID, Long.class))
                .role(claims.get(CLAIMS_ROLE, String.class))
                .build();
    }

    public void parseRefreshToken(String refreshToken) {
        parseToken(refreshToken, jwtProps.getRefreshSecretKey());
    }

    private String issueToken(Claims claims, Duration expiration, SecretKey secretKey) {
        Date now = new Date();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    private Claims parseToken(String token, SecretKey secretKey) {
        Claims payload;
        try {
            payload = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtTokenException(JwtTokenErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtTokenException(JwtTokenErrorCode.INVALID_TOKEN);
        }
        return payload;
    }

}
