package com.gbcontentagency.arlo.users.oauth2;

import com.gbcontentagency.arlo.users.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final int JWT_ACCESS_EXPIRATION_TIME = 1000 * 60 * 10;
    public static final int JWT_REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    private final JwtUtil jwtUtil;

    public CustomSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2UserEntity customUserDetails = (CustomOAuth2UserEntity) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String role = getRole(authentication);
        String nickname = customUserDetails.getName();
        String profileImg = customUserDetails.getProfileImg();

        String accessToken = "BEARER_" + jwtUtil.generateToken(username, "access", role, nickname, profileImg, JWT_ACCESS_EXPIRATION_TIME);
        String refreshToken = "BEARER_" + jwtUtil.generateToken(username, "refresh", role, nickname, profileImg, JWT_REFRESH_EXPIRATION_TIME);

        response.addCookie(createCookie("Authorization", accessToken));
        response.addCookie(createCookie("Refresh-Token", refreshToken));
        response.sendRedirect("https://e6fe-118-176-146-86.ngrok-free.app");
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);

        if (key.equals("Authorization")) {
            cookie.setMaxAge(JWT_ACCESS_EXPIRATION_TIME / 1000);
        } else {
            cookie.setMaxAge(JWT_REFRESH_EXPIRATION_TIME / 1000);

        }

        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private static String getRole(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        return role;
    }

}
