package com.gbcontentagency.arlo.users.jwt;

import com.gbcontentagency.arlo.users.dto.UserDto;
import com.gbcontentagency.arlo.users.oauth2.CustomOAuth2UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {

                    authorization = cookie.getValue();
                    break;
                }
            }
        }

        if (authorization == null || !authorization.startsWith("BEARER ")) {

            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.substring(7);
        if (jwtUtil.isExpired(token)) {

            filterChain.doFilter(request, response);

            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        String nickname = jwtUtil.getNickname(token);
        String profileImg = jwtUtil.getProfileImg(token);

        UserDto userDto = UserDto.builder()
                .username(username)
                .role(role)
                .nickname(nickname)
                .profileImg(profileImg)
                .build();

        CustomOAuth2UserEntity customOAuth2User = new CustomOAuth2UserEntity(userDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}
