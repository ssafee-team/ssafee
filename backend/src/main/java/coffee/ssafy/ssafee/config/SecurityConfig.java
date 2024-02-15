package coffee.ssafy.ssafee.config;

import coffee.ssafy.ssafee.common.OriginProps;
import coffee.ssafy.ssafee.domain.user.service.UserService;
import coffee.ssafy.ssafee.jwt.JwtAuthenticationFilter;
import coffee.ssafy.ssafee.oauth.HttpCookieOAuth2RequestRepository;
import coffee.ssafy.ssafee.oauth.OAuth2RequestResolver;
import coffee.ssafy.ssafee.oauth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProblemSupport problemSupport;
    private final HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;
    private final UserService userService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OriginProps originProps;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/shops/*/orders/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/parties", "/api/v1/parties/*/order/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/shops/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/shops/**", "/api/v1/managers/me").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/shops/**").hasRole("MANAGER")
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
                                .authorizationRequestResolver(authorizationRequestResolver())
                                .authorizationRequestRepository(httpCookieOAuth2RequestRepository))
                        .redirectionEndpoint(config -> config
                                .baseUri("/api/v1/oauth2/callback/**"))
                        .userInfoEndpoint(config -> config
                                .userService(userService))
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

    @Bean
    public OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
        OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository, "/api/v1/oauth2/authorization");
        return new OAuth2RequestResolver(defaultAuthorizationRequestResolver);
    }

}
