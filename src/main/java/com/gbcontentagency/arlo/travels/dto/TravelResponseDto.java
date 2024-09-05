package com.gbcontentagency.arlo.travels.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TravelResponseDto {

    String travelDuration;

    String transportType;

    List<String> mainPurpose;

    List<String> foodType;

    List<String> diningStyle;

    String outdoorPreference;

    String accommodationType;

    List<String> accommodationFacilities;

    String accommodationBudget;

    List<String> specialConsiderations;

    String nickname;

    @Builder
    public TravelResponseDto(String travelDuration, String transportType, List<String> mainPurpose,
                             List<String> foodType, List<String> diningStyle, String outdoorPreference,
                             String accommodationType, List<String> accommodationFacilities,
                             String accommodationBudget, List<String> specialConsiderations, String nickname) {
        this.travelDuration = travelDuration;
        this.transportType = transportType;
        this.mainPurpose = mainPurpose;
        this.foodType = foodType;
        this.diningStyle = diningStyle;
        this.outdoorPreference = outdoorPreference;
        this.accommodationType = accommodationType;
        this.accommodationFacilities = accommodationFacilities;
        this.accommodationBudget = accommodationBudget;
        this.specialConsiderations = specialConsiderations;
        this.nickname = nickname;
    }

}
