package com.kwetter.frits.timelineservice.repository;

import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface TweetTimelineRepository extends MongoRepository<TweetTimeline, String> {
    List<TweetTimeline> findAllByOrderByTweetPostedDesc();
    List<TweetTimeline> findAllByTweetUser_Username(String username);
    void deleteAllByTweetUser_UsernameAndTweetUser_UserId(String username, UUID userId);
}
