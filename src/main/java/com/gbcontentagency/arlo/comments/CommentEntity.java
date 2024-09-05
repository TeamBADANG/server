package com.gbcontentagency.arlo.comments;

import com.gbcontentagency.arlo.comments.dto.CommentRequestDto;
import com.gbcontentagency.arlo.feeds.FeedEntity;
import com.gbcontentagency.arlo.users.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate createdDate;

    @Column
    private LocalDate updatedDate;

    @Column
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private FeedEntity feed;

    @Builder
    public CommentEntity(LocalDate createdDate, LocalDate updatedDate, String comment, UserEntity user, FeedEntity feed) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.comment = comment;
        this.user = user;
        this.feed = feed;
    }

    public static CommentEntity of(CommentRequestDto commentRequestDto, UserEntity user, FeedEntity feed) {
        return CommentEntity.builder()
                .createdDate(commentRequestDto.getCreatedDate())
                .updatedDate(commentRequestDto.getUpdatedDate())
                .comment(commentRequestDto.getComment())
                .user(user)
                .feed(feed)
                .build();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.updatedDate = LocalDate.now();
        this.comment = commentRequestDto.getComment();
    }

}
