package com.gbcontentagency.arlo.feeds.dto;

import com.gbcontentagency.arlo.comments.CommentEntity;
import com.gbcontentagency.arlo.feeds.FeedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
public class FeedResponseDto {

    private Long userId;

    private String nickname;

    private String profileImg;

    private Long feedId;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private String title;

    private String content;

    private String thumbnailUrl;

    private List<String> images;

    private List<CommentEntity> comments;

    @Builder
    public FeedResponseDto(Long userId, String nickname, String profileImg,
                           Long feedId, LocalDate createdDate, LocalDate updatedDate, String title, String content,
                           String thumbnailUrl, List<String> images, List<CommentEntity> comments) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.feedId = feedId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.images = images;
        this.comments = comments;
    }

    public static FeedResponseDto of(FeedEntity savedFeed) {

        return FeedResponseDto.builder()
                .userId(savedFeed.getUser().getId())
                .nickname(savedFeed.getUser().getNickname())
                .profileImg(savedFeed.getUser().getProfileImg())
                .feedId(savedFeed.getId())
                .createdDate(savedFeed.getCreatedDate())
                .updatedDate(savedFeed.getUpdatedDate())
                .title(savedFeed.getThumbnailUrl())
                .content(savedFeed.getContent())
                .thumbnailUrl(savedFeed.getThumbnailUrl())
                .images(savedFeed.getImages())
                .comments(savedFeed.getComments())
                .build();
    }

}
