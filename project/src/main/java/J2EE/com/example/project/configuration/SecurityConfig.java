package J2EE.com.example.project.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import J2EE.com.example.project.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final CustomUserDetailsService userDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

                provider.setPasswordEncoder(passwordEncoder());

                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration) throws Exception {

                return configuration.getAuthenticationManager();
        }

        // =========================
        // CORS
        // =========================
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();

                // Frontend React
                configuration.setAllowedOrigins(
                                List.of("http://localhost:5173"));

                // Các method frontend được phép gọi
                configuration.setAllowedMethods(
                                List.of(
                                                "GET",
                                                "POST",
                                                "PUT",
                                                "DELETE",
                                                "PATCH",
                                                "OPTIONS"));

                // Cho phép Authorization header chứa JWT
                configuration.setAllowedHeaders(
                                List.of("*"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration("/**", configuration);

                return source;
        }

        // =========================
        // SECURITY
        // =========================
        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http) throws Exception {

                http

                                // CORS
                                .cors(cors -> {
                                })

                                // Không dùng CSRF cho REST API + JWT
                                .csrf(csrf -> csrf.disable())

                                // JWT không sử dụng session
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))

                                .authenticationProvider(
                                                authenticationProvider())

                                .authorizeHttpRequests(auth -> auth

                                                // Login / Register không cần JWT
                                                .requestMatchers(
                                                                "/api/v1/auth/register",
                                                                "/api/v1/auth/login")
                                                .permitAll()
                                                // Khách vãng lai được xem danh sách sản phẩm
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/v1/products")
                                                .permitAll()

                                                // Khách vãng lai được xem chi tiết sản phẩm
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/v1/products/**")
                                                .permitAll()

                                                // Các API còn lại bắt buộc đăng nhập
                                                .anyRequest().authenticated())

                                // JWT filter
                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}