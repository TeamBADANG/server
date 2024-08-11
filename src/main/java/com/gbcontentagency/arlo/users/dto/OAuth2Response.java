package com.gbcontentagency.arlo.users.dto;

public interface OAuth2Response {

    String getProvider();

    String getProviderId();

    String getNickname();

    String getProfileImage();

}