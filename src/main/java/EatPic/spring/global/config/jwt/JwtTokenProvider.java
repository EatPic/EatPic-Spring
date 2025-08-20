package EatPic.spring.global.config.jwt;

import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import EatPic.spring.global.config.Properties.Constants;
import EatPic.spring.global.config.Properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private static final String ROLES = "roles";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    // JWT Access Token을 생성하고 반환
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 권한이 비어 있으면 DB에서 사용자 role을 읽어 보정 (임시 해결)
        if (roles.isEmpty()) {
            var user = userRepository.findByEmail(email).orElse(null);
            if (user != null && user.getRole() != null) {
                roles = java.util.List.of("ROLE_" + user.getRole().name());
            }
        }

        return Jwts.builder()
                .setSubject(email)
                .claim("tokenType", "accessToken")
                .claim(ROLES, roles) // 🔹 roles 클레임 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenValidity()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // refreshToeken용 인증 없이 email만
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("tokenType", "refreshToken")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenValidity()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰에서 Claims 객체를 추출하는 핵심 메소드
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            return null; // 유효하지 않은 토큰일 경우 null 반환
        }
    }

    // JWT 토큰이 유효한지 검증
    public boolean validateToken(String token) {
        return getClaims(token) != null;
    }

    // JWT 토큰에서 인증 정보를 추출해서 Spring Security의 Authentication 객체로 변환
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }

        String email = claims.getSubject();

        @SuppressWarnings("unchecked")
        List<String> roleStrings = claims.get(ROLES) instanceof List
                ? (List<String>) claims.get(ROLES)
                : Collections.emptyList();

        List<SimpleGrantedAuthority> authorities = roleStrings.stream()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .toList();

        org.springframework.security.core.userdetails.User principal =
                new org.springframework.security.core.userdetails.User(email, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTH_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.TOKEN_PREFIX)) {
            return bearerToken.substring(Constants.TOKEN_PREFIX.length());
        }
        return null;
    }

    // HttpServletRequest 에서 토큰 값을 추출
    public Authentication extractAuthentication(HttpServletRequest request){
        String accessToken = resolveToken(request);
        if(accessToken == null || !validateAccessToken(accessToken)) {
            throw new ExceptionHandler(ErrorStatus.INVALID_TOKEN);
        }
        return getAuthentication(accessToken);
    }

    // token 유효성 검증
    private boolean validateTokenType(String token, String expectedType) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String type = claims.get("tokenType", String.class);
            return expectedType.equals(type);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // accessToken 유효성 검증
    public boolean validateAccessToken(String accessToken){
        return validateTokenType(accessToken, "accessToken");
    }

    // refreshToken 유효성 검증
    public boolean validateRefreshToken(String refreshToken) {
        return validateTokenType(refreshToken, "refreshToken");
    }
    // token의 email 꺼내기
    public String getSubject(String token) {
        Claims claims = getClaims(token);
        return (claims != null) ? claims.getSubject() : null;
    }

    // token 만료 시간
    public long getExpiredTime(String token) {
        Claims claims = getClaims(token);
        return (claims != null) ? claims.getExpiration().getTime() : 0;
    }
}
