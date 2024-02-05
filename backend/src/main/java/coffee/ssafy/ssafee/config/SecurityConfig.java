package coffee.ssafy.ssafee.config;

import coffee.ssafy.ssafee.jwt.JwtAuthenticationFilter;
import coffee.ssafy.ssafee.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import coffee.ssafy.ssafee.oauth.OAuth2CustomAuthenticationSuccessHandler;
import coffee.ssafy.ssafee.oauth.OAuth2CustomUserService;
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
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2CustomUserService oAuth2CustomUserService;
    private final OAuth2CustomAuthenticationSuccessHandler oAuth2CustomAuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/test").authenticated()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/shops/**", "/api/v1/parties").authenticated() // TODO: 인증 비활성화
                        .requestMatchers(HttpMethod.POST, "/api/v1/shops/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/shops/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/shops/**").authenticated()
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
                                .baseUri("/api/v1/oauth2/authorization")
                                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                        .redirectionEndpoint(config -> config
                                .baseUri("/api/v1/oauth2/callback/**"))
                        .userInfoEndpoint(config -> config
                                .userService(oAuth2CustomUserService))
                        .successHandler(oAuth2CustomAuthenticationSuccessHandler)
                        .failureHandler(problemSupport));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://ssafy.coffee"));
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
