package com.kwetter.frits.timelineservice.repository;

import com.kwetter.frits.timelineservice.entity.FollowTimeline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FollowTimelineRepository extends MongoRepository<FollowTimeline, String> {
    void deleteFollowByUsernameAndFollowingUsername(String username, String followingUsername);
    List<FollowTimeline> findAllByUsername(String username);
    List<FollowTimeline> findAllByFollowingUsername(String username);
    void deleteAllByUsername(String username);
    void deleteAllByFollowingUsername(String username);
}
