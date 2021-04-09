package com.kwetter.frits.timelineservice.repository;

import com.kwetter.frits.timelineservice.entity.UserTimeline;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTimelineRepository extends MongoRepository<UserTimeline, String> {
}
