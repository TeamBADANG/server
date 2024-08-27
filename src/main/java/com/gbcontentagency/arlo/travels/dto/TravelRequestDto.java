package com.gbcontentagency.arlo.travels.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TravelRequestDto {

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

    @Builder
    public TravelRequestDto(String travelDuration, String transportType, List<String> mainPurpose,
                            List<String> foodType, List<String> diningStyle, String outdoorPreference,
                            String accommodationType, List<String> accommodationFacilities, String accommodationBudget,
                            List<String> specialConsiderations) {
        this.travelDuration = (travelDuration != null) ? travelDuration : "당일치기";
        this.transportType = (transportType != null) ? transportType : "대중교통, 자차 옵션 각각 추천";
        this.mainPurpose = (mainPurpose != null) ? mainPurpose : List.of("여행 목적 상관없이 여행 코스 추천");
        this.foodType = (foodType != null) ? foodType : List.of("음식 종류 상관없이 맛집 추천");
        this.diningStyle = (diningStyle != null) ? diningStyle : List.of("음식 스타일 상관없이 맛집 추천");
        this.outdoorPreference = (outdoorPreference != null) ? outdoorPreference : "실내, 실외 등 여행 스타일 상관없이 추천";
        this.accommodationType = (accommodationType != null) ? accommodationType : "숙박 형태 상관없이 추천";
        this.accommodationFacilities = (accommodationFacilities != null) ? accommodationFacilities : List.of("부대시설 상관없이 추천");
        this.accommodationBudget = (accommodationBudget != null) ? accommodationBudget : "예산 상관없이 추천";
        this.specialConsiderations = (specialConsiderations != null) ? specialConsiderations : List.of("별도 고려 사항 없음");
    }

}
