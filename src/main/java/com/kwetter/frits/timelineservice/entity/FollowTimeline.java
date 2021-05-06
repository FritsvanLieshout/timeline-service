package com.kwetter.frits.timelineservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "follow_timeline")
public class FollowTimeline {

    @Id
    private String id;

    private String username;
    private String followingUsername;

    public FollowTimeline() {}

    public FollowTimeline(String username, String followingUsername) {
        this.username = username;
        this.followingUsername = followingUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }
}
