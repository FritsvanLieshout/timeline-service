package com.kwetter.frits.timelineservice.repository;

import com.kwetter.frits.timelineservice.entity.TweetTimeline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface TweetTimeLineRepository extends MongoRepository<TweetTimeline, String> {
}
