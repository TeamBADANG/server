package com.gbcontentagency.arlo.apis;

import com.gbcontentagency.arlo.feeds.FeedService;
import com.gbcontentagency.arlo.feeds.dto.FeedRequestDto;
import com.gbcontentagency.arlo.feeds.dto.FeedResponseDto;
import com.gbcontentagency.arlo.users.UserEntity;
import com.gbcontentagency.arlo.users.UserRepository;
import com.gbcontentagency.arlo.users.oauth2.CustomOAuth2UserEntity;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/feeds")
@RestController
public class FeedController {

    FeedService feedService;
    UserRepository userRepository;

    public FeedController(FeedService feedService, UserRepository userRepository) {
        this.feedService = feedService;
        this.userRepository = userRepository;
    }

    @PostMapping("/")
    public ResponseEntity<FeedResponseDto> createFeed(@AuthenticationPrincipal CustomOAuth2UserEntity oauth2User,
                                                      @Valid @RequestBody FeedRequestDto feedRequestDto) throws AuthenticationException {

        UserEntity user = userRepository.findByUsername(oauth2User.getUsername());
        if (user == null) throw new UsernameNotFoundException("유저를 찾을 수 없습니다. 로그인 정보를 확인하세요.");

        FeedResponseDto resData = feedService.createFeed(user, feedRequestDto);

        return ResponseEntity.ok(resData);
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedResponseDto> getFeed(@PathVariable("feedId") Long feedId) {

        FeedResponseDto resData = feedService.getFeed(feedId);

        return ResponseEntity.ok(resData);
    }

    @PutMapping("/{feedId}")
    public ResponseEntity<FeedResponseDto> updateFeed(@AuthenticationPrincipal CustomOAuth2UserEntity oauth2User,
                                                      @PathVariable("feedId") Long feedId,
                                                      @Valid @RequestBody FeedRequestDto feedRequestDto) throws AuthException {

        UserEntity user = userRepository.findByUsername(oauth2User.getUsername());
        if (user == null) throw new UsernameNotFoundException("유저를 찾을 수 없습니다. 로그인 정보를 확인하세요.");

        FeedResponseDto resData = feedService.updateFeed(user, feedId, feedRequestDto);

        return ResponseEntity.ok(resData);
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<String> deleteFeed(@AuthenticationPrincipal CustomOAuth2UserEntity oauth2User,
                                             @PathVariable("feedId") Long feedId) throws AuthException {

        UserEntity user = userRepository.findByUsername(oauth2User.getUsername());
        if (user == null) throw new UsernameNotFoundException("유저를 찾을 수 없습니다. 로그인 정보를 확인하세요.");

        String resData = feedService.deleteFeed(user, feedId);

        return ResponseEntity.ok(resData);
    }

}
