package com.gbcontentagency.arlo.feeds;

import com.gbcontentagency.arlo.feeds.dto.FeedRequestDto;
import com.gbcontentagency.arlo.feeds.dto.FeedResponseDto;
import com.gbcontentagency.arlo.users.UserEntity;
import jakarta.security.auth.message.AuthException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FeedService {

    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public Page<FeedResponseDto> getAllFeeds(Pageable pageable) {

        List<FeedEntity> allFeeds = feedRepository.findAll();
        allFeeds.sort((Comparator.comparing(FeedEntity::getCreatedDate).reversed()));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allFeeds.size());
        List<FeedEntity> pagedFeeds = allFeeds.subList(start, end);

        return new PageImpl<>(pagedFeeds, pageable, allFeeds.size())
                .map(FeedResponseDto::of);
    }

    public FeedResponseDto createFeed(UserEntity user, FeedRequestDto feedRequestDto) {

        FeedEntity newFeed = FeedEntity.of(user, feedRequestDto);
        FeedEntity savedFeed = feedRepository.save(newFeed);

        FeedResponseDto resData = FeedResponseDto.of(savedFeed);

        return resData;
    }

    public FeedResponseDto getFeed(Long feedId) {

        Optional<FeedEntity> feed = feedRepository.findById(feedId);

        if (feed.isEmpty()) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. feedId: " + feedId);
        }

        FeedResponseDto resData = FeedResponseDto.of(feed.get());

        return resData;
    }

    public FeedResponseDto updateFeed(UserEntity user, Long feedId, FeedRequestDto feedRequestDto) throws AuthException {

        FeedEntity feed = feedValidation(user, feedId);

        feed.update(feedRequestDto);
        feedRepository.save(feed);
        FeedResponseDto resData = FeedResponseDto.of(feed);

        return resData;
    }

    public String deleteFeed(UserEntity user, Long feedId) throws AuthException {

        FeedEntity feed = feedValidation(user, feedId);

        feedRepository.delete(feed);

        return "게시물 삭제에 성공했습니다.";
    }

    private FeedEntity feedValidation(UserEntity user, Long feedId) throws AuthException {
        Optional<FeedEntity> foundFeed = feedRepository.findById(feedId);

        if (foundFeed.isEmpty()) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다. feedId: " + feedId);
        }

        FeedEntity feed = foundFeed.get();

        if (!user.getId().equals(feed.getUser().getId())) {
            throw new AuthException("게시물에 대한 권한이 없습니다.");
        }

        return feed;
    }

}
