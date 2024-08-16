package com.gbcontentagency.arlo.travels;

import com.gbcontentagency.arlo.travels.dto.TravelRequestDto;
import com.gbcontentagency.arlo.travels.dto.TravelResponseDto;
import com.gbcontentagency.arlo.users.UserEntity;
import com.gbcontentagency.arlo.users.UserRepository;
import com.gbcontentagency.arlo.users.oauth2.CustomOAuth2UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TravelService {

    private final TravelRepository travelRepository;

    private final UserRepository userRepository;

    public TravelService(TravelRepository travelRepository, UserRepository userRepository) {
        this.travelRepository = travelRepository;
        this.userRepository = userRepository;
    }

    public TravelResponseDto saveUserPreferences(TravelRequestDto travelRequestDto, CustomOAuth2UserEntity oauth2User) {

        String username = oauth2User.getUsername();
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");

        TravelEntity userPreferences = TravelMapper.toEntity(travelRequestDto, user);
        travelRepository.save(userPreferences);

        TravelResponseDto resData = TravelMapper.toDto(userPreferences, user);

        return resData;
    }

}
