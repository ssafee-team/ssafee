package coffee.ssafy.ssafee.config;

import coffee.ssafy.ssafee.common.OriginProps;
import coffee.ssafy.ssafee.domain.user.entity.UserAuthority;
import coffee.ssafy.ssafee.jwt.JwtAuthenticationFilter;
import coffee.ssafy.ssafee.oauth.HttpCookieOAuth2RequestRepository;
import coffee.ssafy.ssafee.oauth.OAuth2SuccessHandler;
import coffee.ssafy.ssafee.oauth.Oauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProblemSupport problemSupport;
    private final HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;
    private final Oauth2Service oauth2Service;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OriginProps originProps;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/v1/rooms", "/v1/rooms/*/order/**").hasAuthority(UserAuthority.CREATOR.name())
                        .requestMatchers(HttpMethod.GET, "/v1/shops/*/orders/**").hasAuthority(UserAuthority.MANAGER.name())
                        .requestMatchers(HttpMethod.POST, "/v1/shops/**").hasAuthority(UserAuthority.MANAGER.name())
                        .requestMatchers(HttpMethod.PUT, "/v1/shops/**", "/v1/managers/me").hasAuthority(UserAuthority.MANAGER.name())
                        .requestMatchers(HttpMethod.DELETE, "/v1/shops/**").hasAuthority(UserAuthority.MANAGER.name())
                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .oauth2Login(oAuth2LoginConfig -> oAuth2LoginConfig
                        .authorizationEndpoint(config -> config
                                .baseUri("/v1/oauth2/authorize")
                                .authorizationRequestRepository(httpCookieOAuth2RequestRepository))
                        .redirectionEndpoint(config -> config
                                .baseUri("/v1/oauth2/callback/**"))
                        .userInfoEndpoint(config -> config
                                .userService(oauth2Service))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(problemSupport));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(originProps.allowed()));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        configuration.setExposedHeaders(List.of(CorsConfiguration.ALL));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
