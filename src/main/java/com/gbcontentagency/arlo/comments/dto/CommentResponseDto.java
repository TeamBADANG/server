package com.gbcontentagency.arlo.comments.dto;

import com.gbcontentagency.arlo.comments.CommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto {

    private Long feedId;

    private Long commentId;

    private String nickname;

    private String profileImg;

    private LocalDate updatedDate;

    private String content;

    @Builder
    public CommentResponseDto(Long feedId, Long commentId, String nickname, String profileImg, LocalDate updatedDate, String content) {
        this.feedId = feedId;
        this.commentId = commentId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.updatedDate = updatedDate;
        this.content = content;
    }

    public static CommentResponseDto of(CommentEntity comment) {
        return CommentResponseDto.builder()
                .feedId(comment.getFeed().getId())
                .commentId(comment.getId())
                .nickname(comment.getUser().getNickname())
                .profileImg(comment.getUser().getProfileImg())
                .updatedDate(comment.getUpdatedDate())
                .content(comment.getComment())
                .build();
    }

}
