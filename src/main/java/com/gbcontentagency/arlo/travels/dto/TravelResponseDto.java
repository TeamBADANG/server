package com.gbcontentagency.arlo.travels.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
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

}
