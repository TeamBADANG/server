package com.gbcontentagency.arlo.apis;

import com.gbcontentagency.arlo.travels.TravelService;
import com.gbcontentagency.arlo.travels.dto.TravelRequestDto;
import com.gbcontentagency.arlo.travels.dto.TravelResponseDto;
import com.gbcontentagency.arlo.users.oauth2.CustomOAuth2UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/travels")
@RestController
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping("/preferences")
    public ResponseEntity<TravelResponseDto> saveUserPreferences(@RequestBody TravelRequestDto travelRequestDto,
                                                                 @AuthenticationPrincipal CustomOAuth2UserEntity oauth2User) {

        TravelResponseDto resData = travelService.saveUserPreferences(travelRequestDto, oauth2User);

        return ResponseEntity.ok(resData);
    }

}
