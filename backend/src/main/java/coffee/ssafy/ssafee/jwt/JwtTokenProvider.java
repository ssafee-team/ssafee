package coffee.ssafy.ssafee.jwt;

import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import coffee.ssafy.ssafee.jwt.exception.JwtTokenErrorCode;
import coffee.ssafy.ssafee.jwt.exception.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String CLAIMS_ID = "id";
    private static final String CLAIMS_SHOP_ID = "shopId";
    private static final String CLAIMS_AUTHORITY = "authority";
    private final JwtProps jwtProps;

    public String issueManagerAccessToken(JwtPrincipalInfo principal) {
        Claims claims = Jwts.claims()
                .add(CLAIMS_ID, principal.id())
                .add(CLAIMS_SHOP_ID, principal.shopId())
                .add(CLAIMS_AUTHORITY, principal.authority())
                .build();
        return issueToken(claims, jwtProps.getAccessExpiration(), jwtProps.getAccessSecretKey());
    }

    public String issueUserAccessToken(JwtPrincipalInfo principal) {
        Claims claims = Jwts.claims()
                .add(CLAIMS_ID, principal.id())
                .add(CLAIMS_AUTHORITY, principal.authority())
                .build();
        return issueToken(claims, jwtProps.getAccessExpiration(), jwtProps.getAccessSecretKey());
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseToken(accessToken, jwtProps.getAccessSecretKey());
        JwtPrincipalInfo principal = JwtPrincipalInfo.builder()
                .id(claims.get(CLAIMS_ID, Long.class))
                .shopId(claims.get(CLAIMS_SHOP_ID, Long.class))
                .authority(claims.get(CLAIMS_AUTHORITY, String.class))
                .build();
        return new UsernamePasswordAuthenticationToken(principal, null, List.of(principal::authority));
    }

    public String issueRefreshToken() {
        return issueToken(null, jwtProps.getRefreshExpiration(), jwtProps.getRefreshSecretKey());
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
