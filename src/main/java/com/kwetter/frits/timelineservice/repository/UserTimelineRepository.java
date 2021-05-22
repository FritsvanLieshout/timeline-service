package com.kwetter.frits.timelineservice.repository;

import com.kwetter.frits.timelineservice.entity.UserTimeline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface UserTimelineRepository extends MongoRepository<UserTimeline, String> {
    void deleteUserTimelineByUsername(String username);
    UserTimeline findUserTimelineByUsernameAndUserId(String username, UUID userId);
    UserTimeline findUserTimelineByUsername(String username);
}
