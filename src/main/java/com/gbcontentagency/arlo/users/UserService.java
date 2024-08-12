package com.gbcontentagency.arlo.users;

import com.gbcontentagency.arlo.users.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final int JWT_ACCESS_EXPIRATION_TIME = 1000 * 60 * 10;

    private final JwtUtil jwtUtil;

    public UserService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String authorization = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Refresh-Token")) {

                    authorization = cookie.getValue();
                    break;
                }
            }
        }

        if (authorization == null | !authorization.startsWith("BEARER_")) {

            return new ResponseEntity<>("Refresh Token is NULL", HttpStatus.BAD_REQUEST);
        }

        String refreshToken = authorization.substring(7);
        try {

            jwtUtil.isExpired(refreshToken);
        }catch (ExpiredJwtException e) {

            return new ResponseEntity<>("Refresh Token is expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refreshToken);
        if (category ==null || !category.equals("refresh")) {

            return new ResponseEntity<>("Invalid Refresh Token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);
        String nickname = jwtUtil.getNickname(refreshToken);
        String profileImg = jwtUtil.getProfileImg(refreshToken);

        String newAccessToken = "BEARER_" + jwtUtil.generateToken(username, "access", role, nickname, profileImg, JWT_ACCESS_EXPIRATION_TIME);

        response.setHeader("Authorization", newAccessToken);

        return new ResponseEntity<>("CLEAR", HttpStatus.OK);
    }

}
