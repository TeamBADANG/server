package com.gbcontentagency.arlo.users.dto;

import com.gbcontentagency.arlo.users.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    String username;

    String role;

    String nickname;

    String profileImg;

    @Builder
    public UserDto(String username, String role, String nickname, String profileImg) {
        this.username = username;
        this.role = role;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    @Builder
    public UserDto(UserEntity user) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();
    }

}
