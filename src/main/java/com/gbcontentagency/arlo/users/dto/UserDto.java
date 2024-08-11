package com.gbcontentagency.arlo.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    String role;

    String username;

    String nickname;

    String profileImg;

    @Builder
    public UserDto(String role, String username, String nickname, String profileImg) {
        this.role = role;
        this.username = username;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
