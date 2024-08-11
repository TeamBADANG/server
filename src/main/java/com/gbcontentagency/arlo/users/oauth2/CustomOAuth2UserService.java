package com.gbcontentagency.arlo.users.oauth2;

import com.gbcontentagency.arlo.users.UserEntity;
import com.gbcontentagency.arlo.users.UserRepository;
import com.gbcontentagency.arlo.users.dto.GoogleResponse;
import com.gbcontentagency.arlo.users.dto.NaverResponse;
import com.gbcontentagency.arlo.users.dto.OAuth2Response;
import com.gbcontentagency.arlo.users.dto.UserDto;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2Response oAuth2Response = null;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else {

            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        UserEntity foundUser = userRepository.findByUsername(username);

        if (foundUser == null) {

            UserEntity newUser = updateUser(username, oAuth2Response);

            userRepository.save(newUser);

            UserDto userDto = createOAuthUserDto(username, oAuth2Response);

            return new CustomOAuth2UserEntity(userDto);
        } else {
            foundUser = updateUser(username, oAuth2Response);

            userRepository.save(foundUser);

            UserDto userDto = createOAuthUserDto(username, oAuth2Response);

            return new CustomOAuth2UserEntity(userDto);
        }
    }

    private static UserEntity updateUser(String username, OAuth2Response oAuth2Response) {

        return UserEntity.builder()
                .username(username)
                .oAuth2Response(oAuth2Response)
                .build();
    }

    private static UserDto createOAuthUserDto(String username, OAuth2Response oAuth2Response) {

        return UserDto.builder()
                .role("ROLE_USER")
                .username(username)
                .nickname(oAuth2Response.getNickname())
                .profileImg(oAuth2Response.getProfileImage())
                .build();
    }

}
