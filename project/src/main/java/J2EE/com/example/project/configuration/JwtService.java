package J2EE.com.example.project.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

        @Value("${spring.jwt.secret}")
        private String secretKey;

        @Value("${spring.jwt.expiration}")
        private long expiration;

        private SecretKey getSigningKey() {

                return Keys.hmacShaKeyFor(
                                secretKey.getBytes(StandardCharsets.UTF_8));
        }

        public String generateToken(
                        UserDetails userDetails,
                        String role) {

                Map<String, Object> claims = new HashMap<>();

                claims.put("role", role);

                return Jwts.builder()
                                .claims(claims)
                                .subject(userDetails.getUsername())
                                .issuedAt(new Date())
                                .expiration(
                                                new Date(
                                                                System.currentTimeMillis()
                                                                                + expiration))
                                .signWith(getSigningKey())
                                .compact();
        }

        public String extractUsername(String token) {

                return extractClaim(
                                token,
                                Claims::getSubject);
        }

        public String extractRole(String token) {

                return extractAllClaims(token)
                                .get("role", String.class);
        }

        public boolean isTokenValid(
                        String token,
                        UserDetails userDetails) {

                String username = extractUsername(token);

                return username.equals(
                                userDetails.getUsername())
                                && !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {

                return extractExpiration(token)
                                .before(new Date());
        }

        private Date extractExpiration(String token) {

                return extractClaim(
                                token,
                                Claims::getExpiration);
        }

        private <T> T extractClaim(
                        String token,
                        Function<Claims, T> resolver) {

                Claims claims = extractAllClaims(token);

                return resolver.apply(claims);
        }

        private Claims extractAllClaims(
                        String token) {

                return Jwts.parser()
                                .verifyWith(getSigningKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
        }
}