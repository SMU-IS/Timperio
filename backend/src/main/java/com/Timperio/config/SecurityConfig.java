package com.Timperio.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.Timperio.constant.UrlConstant;
import com.Timperio.enums.ErrorMessage;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final ApplicationConfig applicationConfig;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Value("${SERVER}")
    private String serverUrl;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(UrlConstant.API_VERSION + "/auth/login", "/v3/api-docs/**", "/swagger-ui/**")
                        .permitAll()

                        .requestMatchers(UrlConstant.API_VERSION + "/purchaseHistory")
                        .hasAuthority("ACCESS AND FILTER PURCHASE HISTORY")

                        .requestMatchers(UrlConstant.API_VERSION + "/export")
                        .hasAuthority("EXPORT FILTERED DATA")

                        .requestMatchers(UrlConstant.API_VERSION + "/customers/**")
                        .hasAnyAuthority("VIEW SALES METRICS", "SEGMENT CUSTOMERS BY SPENDING")

                        .requestMatchers(UrlConstant.API_VERSION + "/newsletter/**")
                        .hasAnyAuthority("CREATE AND SEND NEWSLETTER", "FORMAT NEWSLETTER TEMPLATE")

                        .anyRequest().authenticated())

                .exceptionHandling(
                        exceptionHandling -> exceptionHandling.accessDeniedHandler(customAccessDeniedHandler())
                                .authenticationEntryPoint(customAuthenticationEntryPoint()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(applicationConfig.authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        
        return http.build();
    }

    private AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(ErrorMessage.UNAUTHORIZED.getMessage());
        };
    }

    private AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(ErrorMessage.FORBIDDEN.getMessage());
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(serverUrl));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
