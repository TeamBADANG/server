package com.gbcontentagency.arlo.travels;

import com.gbcontentagency.arlo.travels.dto.TravelRequestDto;
import com.gbcontentagency.arlo.travels.dto.TravelResponseDto;
import com.gbcontentagency.arlo.users.UserEntity;

public class TravelMapper {

    public static TravelEntity toEntity(TravelRequestDto travelDto, UserEntity user) {

        return TravelEntity.builder()
                .travelDuration(travelDto.getTravelDuration())
                .transportType(travelDto.getTransportType())
                .mainPurpose(travelDto.getMainPurpose())
                .foodType(travelDto.getFoodType())
                .diningStyle(travelDto.getDiningStyle())
                .outdoorPreference(travelDto.getOutdoorPreference())
                .accommodationType(travelDto.getAccommodationType())
                .accommodationFacilities(travelDto.getAccommodationFacilities())
                .accommodationBudget(travelDto.getAccommodationBudget())
                .specialConsiderations(travelDto.getSpecialConsiderations())
                .user(user)
                .build();
    }

    public static TravelResponseDto toDto(TravelEntity travelEntity, UserEntity user) {

        return TravelResponseDto.builder()
                .travelDuration(travelEntity.getTravelDuration())
                .transportType(travelEntity.getTransportType())
                .mainPurpose(travelEntity.getMainPurpose())
                .foodType(travelEntity.getFoodType())
                .diningStyle(travelEntity.getDiningStyle())
                .outdoorPreference(travelEntity.getOutdoorPreference())
                .accommodationType(travelEntity.getAccommodationType())
                .accommodationFacilities(travelEntity.getAccommodationFacilities())
                .accommodationBudget(travelEntity.getAccommodationBudget())
                .specialConsiderations(travelEntity.getSpecialConsiderations())
                .nickname(user.getNickname())
                .build();
    }

}
