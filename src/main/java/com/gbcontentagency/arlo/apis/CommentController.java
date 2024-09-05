package com.gbcontentagency.arlo.apis;

import com.gbcontentagency.arlo.comments.CommentService;
import com.gbcontentagency.arlo.comments.dto.CommentRequestDto;
import com.gbcontentagency.arlo.comments.dto.CommentResponseDto;
import com.gbcontentagency.arlo.users.UserEntity;
import com.gbcontentagency.arlo.users.UserRepository;
import com.gbcontentagency.arlo.users.oauth2.CustomOAuth2UserEntity;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    public CommentController(CommentService commentService, UserRepository userRepository) {
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<Page<CommentResponseDto>> getAllComments(@PathVariable("feedId") Long feedId,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {

        if (page <= 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("id")));

        Page<CommentResponseDto> resData = commentService.getAllComments(feedId, pageable);

        return ResponseEntity.ok(resData);
    }

    @PostMapping("/{feedId}")
    public ResponseEntity<CommentResponseDto> createComment(@AuthenticationPrincipal CustomOAuth2UserEntity oauth2User,
                                                            @PathVariable Long feedId,
                                                            @Valid @RequestBody CommentRequestDto commentRequestDto) {

        UserEntity user = userRepository.findByUsername(oauth2User.getUsername());
        if (user == null) throw new UsernameNotFoundException("유저를 찾을 수 없습니다. 로그인 정보를 확인하세요.");

        CommentResponseDto resData = commentService.createComment(user, feedId, commentRequestDto);

        return ResponseEntity.ok(resData);
    }

    @PutMapping("/{feedId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@AuthenticationPrincipal CustomOAuth2UserEntity oauth2User,
                                                            @PathVariable Long feedId,
                                                            @PathVariable Long commentId,
                                                            @Valid @RequestBody CommentRequestDto commentRequestDto) throws AuthException {

        UserEntity user = userRepository.findByUsername(oauth2User.getUsername());
        if (user == null) throw new UsernameNotFoundException("유저를 찾을 수 없습니다. 로그인 정보를 확인하세요.");

        CommentResponseDto resData = commentService.updateComment(user, feedId, commentId, commentRequestDto);

        return ResponseEntity.ok(resData);
    }

    @DeleteMapping("/{feedId}/{commentId}")
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal CustomOAuth2UserEntity oauth2User,
                                                            @PathVariable Long feedId,
                                                            @PathVariable Long commentId) throws AuthException {

        UserEntity user = userRepository.findByUsername(oauth2User.getUsername());
        if (user == null) throw new UsernameNotFoundException("유저를 찾을 수 없습니다. 로그인 정보를 확인하세요.");

        String resData = commentService.deleteComment(user, feedId, commentId);

        return ResponseEntity.ok(resData);
    }

}
