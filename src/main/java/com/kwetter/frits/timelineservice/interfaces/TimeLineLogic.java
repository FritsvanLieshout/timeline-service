package com.kwetter.frits.timelineservice.interfaces;

import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface TimeLineLogic {
    List<TweetTimeline> findAllOrderByDesc();
    List<TweetTimeline> findTweetsByFollowing(String username);
    List<TweetTimeline> findOwnTweets(String username);
}
