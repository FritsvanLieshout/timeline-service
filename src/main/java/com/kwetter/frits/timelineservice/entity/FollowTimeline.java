package com.kwetter.frits.timelineservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "follow_timeline")
public class FollowTimeline {

    @Id
    private String id;

    private String username;
    private String followUsername;

    public FollowTimeline() {}

    public FollowTimeline(String username, String followUsername) {
        this.username = username;
        this.followUsername = followUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowUsername() {
        return followUsername;
    }

    public void setFollowUsername(String followUsername) {
        this.followUsername = followUsername;
    }
}
