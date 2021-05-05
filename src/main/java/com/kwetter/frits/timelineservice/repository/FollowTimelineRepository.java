package com.kwetter.frits.timelineservice.repository;

import com.kwetter.frits.timelineservice.entity.FollowTimeline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface FollowTimelineRepository extends MongoRepository<FollowTimeline, String> {
    void deleteFollowByUsernameAndFollowUsername(String username, String followUsername);
}
