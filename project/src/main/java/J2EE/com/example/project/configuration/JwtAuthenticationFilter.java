package J2EE.com.example.project.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import J2EE.com.example.project.service.impl.CustomUserDetailsService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
                extends OncePerRequestFilter {

        private final JwtService jwtService;

        private final CustomUserDetailsService userDetailsService;

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {

                final String authHeader = request.getHeader("Authorization");

                if (authHeader == null
                                || !authHeader.startsWith("Bearer ")) {

                        filterChain.doFilter(
                                        request,
                                        response);

                        return;
                }

                String token = authHeader.substring(7);

                String email;

                try {

                        email = jwtService.extractUsername(token);

                } catch (Exception e) {

                        filterChain.doFilter(
                                        request,
                                        response);

                        return;
                }

                if (email != null
                                && SecurityContextHolder
                                                .getContext()
                                                .getAuthentication() == null) {

                        UserDetails userDetails = userDetailsService
                                        .loadUserByUsername(email);
                        System.out.println("========== JWT DEBUG ==========");
                        System.out.println("Email: " + email);
                        System.out.println("Token valid: " +
                                        jwtService.isTokenValid(token, userDetails));
                        System.out.println("Enabled: " + userDetails.isEnabled());
                        System.out.println("Authorities: " + userDetails.getAuthorities());

                        if (jwtService.isTokenValid(
                                        token,
                                        userDetails)) {

                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                userDetails,
                                                null,
                                                userDetails.getAuthorities());

                                authentication.setDetails(
                                                new WebAuthenticationDetailsSource()
                                                                .buildDetails(request));

                                SecurityContextHolder
                                                .getContext()
                                                .setAuthentication(
                                                                authentication);
                                System.out.println("Authentication: " +
                                                SecurityContextHolder.getContext()
                                                                .getAuthentication().isAuthenticated());
                        }
                }

                filterChain.doFilter(
                                request,
                                response);
        }
}