package com.kwetter.frits.timelineservice.repository;

import com.kwetter.frits.timelineservice.entity.UserTimeline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserTimelineRepository extends MongoRepository<UserTimeline, String> {
}
