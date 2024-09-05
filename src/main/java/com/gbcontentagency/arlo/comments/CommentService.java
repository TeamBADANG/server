package com.gbcontentagency.arlo.comments;

import com.gbcontentagency.arlo.comments.dto.CommentRequestDto;
import com.gbcontentagency.arlo.comments.dto.CommentResponseDto;
import com.gbcontentagency.arlo.feeds.FeedEntity;
import com.gbcontentagency.arlo.feeds.FeedRepository;
import com.gbcontentagency.arlo.users.UserEntity;
import jakarta.security.auth.message.AuthException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;

    public CommentService(FeedRepository feedRepository, CommentRepository commentRepository) {
        this.feedRepository = feedRepository;
        this.commentRepository = commentRepository;
    }

    public Page<CommentResponseDto> getAllComments(Long feedId, Pageable pageable) {

        Optional<FeedEntity> foundFeed = feedRepository.findById(feedId);
        if (foundFeed.isEmpty()) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. feedId: " + feedId);
        }
        FeedEntity feed = foundFeed.get();

        Page<CommentEntity> allComments = commentRepository.findAllByFeed(feed, pageable);
        Page<CommentResponseDto> resData = allComments.map(CommentResponseDto::of);

        return resData;
    }

    public CommentResponseDto createComment(UserEntity user, Long feedId, CommentRequestDto commentRequestDto) {

        Optional<FeedEntity> foundFeed = feedRepository.findById(feedId);
        if (foundFeed.isEmpty()) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. feedId: " + feedId);
        }
        FeedEntity feed = foundFeed.get();

        CommentEntity newComment = CommentEntity.of(commentRequestDto, user, feed);
        CommentEntity savedComment = commentRepository.save(newComment);

        CommentResponseDto resData = CommentResponseDto.of(savedComment);

        return resData;
    }

    public CommentResponseDto updateComment(UserEntity user, Long feedId, Long commentId, CommentRequestDto commentRequestDto) throws AuthException {

        CommentEntity comment = commentValidation(user, feedId, commentId);

        comment.update(commentRequestDto);
        commentRepository.save(comment);
        CommentResponseDto resData = CommentResponseDto.of(comment);

        return resData;
    }

    public String deleteComment(UserEntity user, Long feedId, Long commentId) throws AuthException {

        CommentEntity comment = commentValidation(user, feedId, commentId);

        commentRepository.delete(comment);

        return "댓글 삭제에 성공했습니다.";
    }

    private CommentEntity commentValidation(UserEntity user, Long feedId, Long commentId) throws AuthException {
        Optional<FeedEntity> foundFeed = feedRepository.findById(feedId);
        if (foundFeed.isEmpty()) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. feedId: " + feedId);
        }
        FeedEntity feed = foundFeed.get();

        Optional<CommentEntity> foundComment = commentRepository.findByFeedAndId(feed, commentId);
        if (foundComment.isEmpty()) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다. commentId: " + commentId);
        }
        CommentEntity comment = foundComment.get();

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new AuthException("댓글에 대한 권한이 없습니다.");
        }

        return comment;
    }

}
