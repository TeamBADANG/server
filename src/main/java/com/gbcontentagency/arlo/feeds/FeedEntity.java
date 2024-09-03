package com.gbcontentagency.arlo.feeds;

import com.gbcontentagency.arlo.comments.CommentEntity;
import com.gbcontentagency.arlo.feeds.dto.FeedRequestDto;
import com.gbcontentagency.arlo.users.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "feeds")
@Entity
public class FeedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private LocalDate createdDate;

    @Column
    private LocalDate updatedDate;

    @Column
    private String thumbnailUrl;

    @ElementCollection
    private List<String> images;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

    @Builder
    public FeedEntity(UserEntity user, LocalDate createdDate, LocalDate updatedDate, String title, String content, String thumbnailUrl, List<String> images) {
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.images = images;
        this.comments = (comments != null) ? comments : new ArrayList<>();
    }

    public static FeedEntity of(UserEntity user, FeedRequestDto feedRequestDto) {
        return FeedEntity.builder()
                .user(user)
                .createdDate(feedRequestDto.getCreatedDate())
                .updatedDate(feedRequestDto.getUpdatedDate())
                .title(feedRequestDto.getTitle())
                .content(feedRequestDto.getContent())
                .thumbnailUrl(feedRequestDto.getThumbnailUrl())
                .images(feedRequestDto.getImages())
                .build();
    }

    public void update(FeedRequestDto feedRequestDto) {
        this.updatedDate = LocalDate.now();
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
        this.thumbnailUrl = feedRequestDto.getThumbnailUrl();
        this.images = feedRequestDto.getImages();
    }

}
