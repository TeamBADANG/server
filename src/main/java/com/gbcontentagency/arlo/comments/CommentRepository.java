package com.gbcontentagency.arlo.comments;

import com.gbcontentagency.arlo.feeds.FeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT c FROM CommentEntity c WHERE c.feed = :feed")
    Page<CommentEntity> findAllByFeed(@Param("feed") FeedEntity feed, Pageable pageable);


    Optional<CommentEntity> findByFeedAndId(FeedEntity feed, Long commentId);
}
